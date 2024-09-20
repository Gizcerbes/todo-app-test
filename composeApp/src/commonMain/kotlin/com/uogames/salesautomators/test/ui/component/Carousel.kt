package com.uogames.salesautomators.test.ui.component

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.uogames.salesautomators.test.util.SizeObserver.toDP
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

class Carousel(
    scope: CoroutineScope
) {

    // private val scope = CoroutineScope(Dispatchers.IO)

    data class Result(
        val position: Int,
        val selected: Boolean,
        val isDrugging: Boolean,
        val progress: Float,
        val modifier: Modifier
    )

    var count = MutableStateFlow(1)
    var position = MutableStateFlow(0)
    var scale = MutableStateFlow(0.1f)
    var inside = MutableStateFlow(false)
    var itemHeight = MutableStateFlow(30.dp)
    var countOnPage = MutableStateFlow(7)

    private var _isDragging = MutableStateFlow(false)
    val isDragging = _isDragging.asStateFlow()
    private val iRadius = itemHeight.combine(countOnPage) { f, s ->
        getRadius(f, s)
    }
    private val eRadius = iRadius.combine(itemHeight) { f, s ->
        sqrt(f.value * f.value - s.value / 2 * s.value / 2)
    }
    private val angle = countOnPage
        .map { 180.0 / it }
        .stateIn(scope, SharingStarted.WhileSubscribed(), 1.0)

    private val rotation = MutableStateFlow(0.0)

    private val shift = MutableStateFlow(0)

    init {
        count
            .combine(angle) { _, v -> v }
            .combine(position) { f, s ->
                //val p = getPosition(rotation.value, f, count.value)
                //rotation.value += angle.value * (p - s) + 0.00000001
                rotation.value = (-f * s) + 0.0000001
            }.launchIn(scope)
    }

    @Composable
    operator fun invoke(
        onFinish: (position: Int) -> Unit = {},
        modifier: Modifier = Modifier,
        box: @Composable (result: Result) -> Unit
    ) {
        val count = count.collectAsState()
        val scale = scale.collectAsState()
        val inside = inside.collectAsState()
        val itemHeight = itemHeight.collectAsState()
        val countOnPage = countOnPage.collectAsState()
        val shift = shift.collectAsState()
        val anim by rotation.collectAsState() //animateFloatAsState(rotation.collectAsState().value.toFloat())
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.heightIn(iRadius.collectAsState(0.dp).value * 2)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            val r = rotation.value % angle.value + 0.01
                            //val r = ((rotation.value % angle.value)).toInt()
                            val hangle = angle.value / 2
                            when {
                                r > 0 && r <= hangle -> rotation.value -= r
                                r > hangle -> rotation.value += angle.value - r
                                r < 0 && r >= -hangle -> rotation.value -= r
                                r < -hangle -> rotation.value -= angle.value + r
                            }
                            onFinish(getPosition(rotation.value, angle.value, count.value))
                            _isDragging.value = false
                        },
                        onDragStart = {
                            _isDragging.value = true
                        }
                    ) { change, dragAmount ->
                        rotation.value += (dragAmount.y.toDP().value / itemHeight.value.value) * angle.value
                    }
                }
        ) {
            for (i in 0 until countOnPage.value) {
                var pos = (-(anim / angle.value) % count.value).toInt()
                pos = if (pos < 0) count.value + pos else pos
                var p = (pos + i - countOnPage.value / 2) % count.value + shift.value
                p = when {
                    p < 0 -> count.value + p
                    p > count.value -> p - count.value
                    else -> p
                }
                var ang = (angle.value * i - 90) + (anim % angle.value) + angle.value / 2
                if (ang.absoluteValue > 90) ang = 90f.toDouble()
                val sres = sin((ang / 180f * PI))
                box(
                    Result(
                        position = p,
                        selected = (sres.toFloat() * -90f).absoluteValue < angle.value / 2,
                        isDrugging = anim.toFloat() != rotation.value.toFloat(),
                        progress = sres.absoluteValue.toFloat(),
                        modifier = Modifier
                            .offset(y = (eRadius.collectAsState(0f).value * sres).dp)
                            .graphicsLayer {
                                rotationX = sres.toFloat() * if (inside.value) 90f else -90f
                                scaleX = 1f - sres.toFloat().absoluteValue * scale.value
                            }
                    )
                )
            }
        }

    }

    private fun getRadius(
        itemHeight: Dp,
        countOnPage: Int
    ) = ((itemHeight.value / 2) / sin((90f / countOnPage) / 180 * PI)).dp

    private fun getPosition(rotation: Double, angle: Double, count: Int): Int {
        var pos = (-(rotation / angle) % count).roundToInt() + shift.value
        pos = if (pos < 0) count + pos else pos
        return when {
            pos < 0 -> count + pos
            else -> pos
        }
    }

}