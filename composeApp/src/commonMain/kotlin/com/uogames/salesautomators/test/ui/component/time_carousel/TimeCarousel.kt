package com.uogames.salesautomators.test.ui.component.time_carousel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.uogames.salesautomators.test.ui.component.Carousel
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.clone
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.current
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.daysInMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.stringArrayResource
import salesautomator_test.composeapp.generated.resources.Res
import salesautomator_test.composeapp.generated.resources.date_time
import salesautomator_test.composeapp.generated.resources.time_format

object TimeCarousel {

    enum class HourClock {
        FORMAT_12,
        FORMAT_24
    }

    enum class HourPart {
        AM,
        PM
    }

    private val dayCarousel = Carousel(CoroutineScope(Dispatchers.IO)).apply {
        this.countOnPage.value = 7
        this.itemHeight.value = 45.dp
    }
    private val monthCarousel = Carousel(CoroutineScope(Dispatchers.IO)).apply {
        this.countOnPage.value = 7
        this.itemHeight.value = 45.dp
        this.count.value = 12
    }
    private val yearCarousel = Carousel(CoroutineScope(Dispatchers.IO)).apply {
        this.countOnPage.value = 7
        this.itemHeight.value = 45.dp
    }

    private val hourCarousel = Carousel(CoroutineScope(Dispatchers.IO)).apply {
        this.countOnPage.value = 7
        this.itemHeight.value = 45.dp
        this.count.value = 24
    }

    private val minuteCarousel = Carousel(CoroutineScope(Dispatchers.IO)).apply {
        this.countOnPage.value = 7
        this.itemHeight.value = 45.dp
        this.count.value = 60
    }


    @Composable
    operator fun invoke(
        startTime: LocalDateTime,
        hourClock: HourClock = HourClock.FORMAT_24,
        borderColor: Color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
        onTimeChanged: (LocalDateTime) -> Unit,
        modifier: Modifier = Modifier
    ) {
        var hClock by remember { mutableStateOf(hourClock) }
        var hPart by remember { mutableStateOf(HourPart.AM) }
        val min = LocalDateTime.current().year
        val max = LocalDateTime.current().year + 50
        val dataRange = min..max
        var time by remember { mutableStateOf(startTime) }
        LaunchedEffect(time) {
            monthCarousel.position.value = time.monthNumber - 1
            yearCarousel.position.value = time.year - dataRange.first
            dayCarousel.count.value = time.daysInMonth()
            dayCarousel.position.value = time.dayOfMonth - 1
            hourCarousel.position.value = time.hour
            minuteCarousel.position.value = time.minute
            hPart = if (time.hour >= 12) HourPart.PM else HourPart.AM
            onTimeChanged(time)
        }
        LaunchedEffect(hClock) {
            hourCarousel.count.value = when (hClock) {
                HourClock.FORMAT_24 -> 24
                HourClock.FORMAT_12 -> 12
            }
        }

        yearCarousel.apply {
            this.count.value = dataRange.count()
        }

        Column(
            modifier = modifier
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val dataTimeNames = stringArrayResource( Res.array.date_time)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(dataTimeNames[0], modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                            Text(dataTimeNames[1], modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                            Text(dataTimeNames[2], modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        }
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(
                            //horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(dataTimeNames[3], modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                            Text(dataTimeNames[4], modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                        }
                    }
                }
            }

            Row {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Card(
                        border = BorderStroke(
                            1.dp,
                            color = borderColor
                        ),
                        shape = RoundedCornerShape(3.dp),
                        contentColor = Color.Transparent,
                        elevation = 0.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(50.dp)
                    ) { }

                    Row {
                        yearCarousel(
                            onFinish = {
                                val dim = daysInMonth(dataRange.first + it, time.month)
                                time = time.clone(
                                    year = dataRange.first + it,
                                    dayOfMonth = if (dim >= time.dayOfMonth) time.dayOfMonth else 1
                                )
                            },
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Text(
                                text = (dataRange.first + it.position).toString(),
                                modifier = it.modifier.graphicsLayer {
                                    alpha = 1f - it.progress + 0.1f
                                },
                                style = if (it.selected) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
                            )
                        }
                        monthCarousel(
                            onFinish = {
                                val dim = daysInMonth(time.year, Month.entries[it])
                                time = time.clone(
                                    monthNumber = it + 1,
                                    dayOfMonth = if (dim >= time.dayOfMonth) time.dayOfMonth else 1
                                )
                            },
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Text(
                                text = (it.position + 1).toString(),
                                modifier = it.modifier.graphicsLayer {
                                    alpha = 1f - it.progress + 0.1f
                                },
                                style = if (it.selected) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
                            )
                        }
                        dayCarousel(
                            onFinish = {
                                val dim = time.daysInMonth()
                                time = time.clone(
                                    dayOfMonth = if (dim >= it + 1) it + 1 else 1
                                )
                            },
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        ) {
                            Text(
                                text = (it.position + 1).toString(),
                                modifier = it.modifier.graphicsLayer {
                                    alpha = 1f - it.progress + 0.1f
                                },
                                style = if (it.selected) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.weight(0.5f)
                ) {
                    Card(
                        border = BorderStroke(
                            1.dp,
                            borderColor
                        ),
                        shape = RoundedCornerShape(3.dp),
                        contentColor = Color.Transparent,
                        elevation = 0.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(50.dp)
                    ) { }

                    Row {
                        hourCarousel(
                            onFinish = {
                                time = time.clone(
                                    hour =  when {
                                        hClock == HourClock.FORMAT_12 && hPart == HourPart.PM -> it + 12
                                        else -> it
                                    }
                                )
                            },
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = it.position.toString(),
                                modifier = it.modifier.graphicsLayer {
                                    alpha = 1f - it.progress + 0.1f
                                },
                                style = if (it.selected) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
                            )
                        }

                        minuteCarousel(
                            onFinish = {
                                time = time.clone(
                                    minute = it
                                )
                            },
                            modifier = Modifier.weight(0.5f)
                        ) {
                            Text(
                                text = it.position.toString(),
                                modifier = it.modifier.graphicsLayer {
                                    alpha = 1f - it.progress + 0.1f
                                },
                                style = if (it.selected) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }


            Row(
                modifier = Modifier.padding(horizontal = 13.dp).align(Alignment.End)
            ) {
                val timeFormatNames = stringArrayResource(Res.array.time_format)
                if (hClock == HourClock.FORMAT_12) {
                    Text(
                        timeFormatNames[2],
                        color = if (hPart == HourPart.AM) MaterialTheme.colors.onPrimary else Color.Unspecified,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (hPart == HourPart.AM) MaterialTheme.colors.primary else Color.Transparent)
                            .clickable {
                                if (hPart == HourPart.PM) time = time.clone(hour = time.hour - 12)
                                hPart = HourPart.AM
                            }.padding(12.dp)

                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        timeFormatNames[3],
                        color = if (hPart == HourPart.PM) MaterialTheme.colors.onPrimary else Color.Unspecified,
                        style = MaterialTheme.typography.button,
                        modifier = Modifier
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (hPart == HourPart.PM) MaterialTheme.colors.primary else Color.Transparent)
                            .clickable {
                                if (hPart == HourPart.AM) time = time.clone(hour = time.hour + 12)
                                hPart = HourPart.PM
                            }.padding(12.dp)

                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    timeFormatNames[0],
                    color = if (hClock == HourClock.FORMAT_12) MaterialTheme.colors.onPrimary else Color.Unspecified,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (hClock == HourClock.FORMAT_12) MaterialTheme.colors.primary else Color.Transparent)
                        .clickable {
                            hClock = HourClock.FORMAT_12
                        }.padding(12.dp)

                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    timeFormatNames[1],
                    color = if (hClock == HourClock.FORMAT_24) MaterialTheme.colors.onPrimary else Color.Unspecified,
                    style = MaterialTheme.typography.button,
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (hClock == HourClock.FORMAT_24) MaterialTheme.colors.primary else Color.Transparent)
                        .clickable {
                            hClock = HourClock.FORMAT_24
                        }.padding(12.dp)
                )
            }

        }
    }
}