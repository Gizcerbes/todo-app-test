package com.uogames.salesautomators.test.ui.screen.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uogames.salesautomators.test.di.ViewModelModule
import com.uogames.salesautomators.test.ui.component.Carousel
import com.uogames.salesautomators.test.ui.component.TextButton
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.toLocalDateTime
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.toText
import com.uogames.salesautomators.test.util.SizeObserver.toDP
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import salesautomator_test.composeapp.generated.resources.Res
import salesautomator_test.composeapp.generated.resources.arrow_back_24px
import salesautomator_test.composeapp.generated.resources.delete
import salesautomator_test.composeapp.generated.resources.delete_24px
import salesautomator_test.composeapp.generated.resources.detail_title
import salesautomator_test.composeapp.generated.resources.edit
import salesautomator_test.composeapp.generated.resources.edit_24px
import salesautomator_test.composeapp.generated.resources.save
import salesautomator_test.composeapp.generated.resources.status
import salesautomator_test.composeapp.generated.resources.task_statuses

object ScreenDetail {


    @Composable
    operator fun invoke(
        task: Task,
        onBack: () -> Unit,
        onEdit: (Task) -> Unit,
        vm: DetailViewModel = viewModel { ViewModelModule.detailVM },
        modifier: Modifier = Modifier.fillMaxSize()
    ) {
        val scope = rememberCoroutineScope()
        vm.set(task)
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = modifier
        ) {
            item {
                TopAppBar(
                    title = { Text(stringResource(Res.string.detail_title)) },
                    navigationIcon = {
                        IconButton(
                            onClick = onBack
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.arrow_back_24px),
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        TextButton(
                            icon = painterResource(Res.drawable.delete_24px),
                            text = stringResource(Res.string.delete),
                            onClick = {
                                scope.launch {
                                    vm.delete()
                                    onBack()
                                }
                            }
                        )
                        TextButton(
                            icon = painterResource(Res.drawable.edit_24px),
                            text = stringResource(Res.string.edit),
                            onClick = {
                                onEdit(task)
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }

            item { Divider(modifier = Modifier.fillMaxWidth()) }

            item {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                    ) {
                        Column(
                            modifier = Modifier.weight(1f).heightIn(186.dp)
                        ) {
                            Text(task.title, style = MaterialTheme.typography.h6)
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(task.location)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                task.endDateAndTimeUTC.toLocalDateTime(TimeZone.currentSystemDefault()).toText(),
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(0.5f)
                        ) {

                            Text(stringResource(Res.string.status))

                            Box(
                                contentAlignment = Alignment.Center
                            ) {
                                Card(
                                    border = BorderStroke(
                                        1.dp,
                                        color = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
                                    ),
                                    shape = RoundedCornerShape(3.dp),
                                    contentColor = Color.Transparent,
                                    elevation = 0.dp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(50.dp)
                                ) { }

                                val arr = stringArrayResource(Res.array.task_statuses)
                                val carousel = Carousel(rememberCoroutineScope()).apply {
                                    this.count.value = arr.size
                                    this.itemHeight.value = 50.dp
                                    this.countOnPage.value = 5
                                    this.position.value = task.status.ordinal
                                }
                                val scope = rememberCoroutineScope()
                                carousel(
                                    onFinish = { scope.launch { vm.changeStatus(TaskStatus.entries[it]) }  },
                                    modifier = Modifier.fillMaxWidth()
                                ) { r ->

                                    Text(
                                        text = arr[r.position],
                                        modifier = r.modifier.graphicsLayer {
                                            alpha = 1f - r.progress + 0.1f
                                        },
                                        style = if (r.selected) MaterialTheme.typography.body1 else MaterialTheme.typography.body2
                                    )
                                }
                            }
                        }
                    }

                    Divider(modifier = Modifier.fillMaxWidth())

                    Text(task.description)

                }
            }


        }
    }


}