package com.uogames.salesautomators.test.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uogames.salesautomators.test.di.ViewModelModule
import com.uogames.salesautomators.test.ui.component.RoundedCheckbox
import com.uogames.salesautomators.test.ui.component.TaskCard
import com.uogames.salesautomators.test.ui.component.TextButton
import com.uogames.selesautomators.test.common.model.SortType
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus
import com.uogames.selesautomators.test.common.model.TimeSort
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import salesautomator_test.composeapp.generated.resources.Res
import salesautomator_test.composeapp.generated.resources.add
import salesautomator_test.composeapp.generated.resources.add_24px
import salesautomator_test.composeapp.generated.resources.ascending
import salesautomator_test.composeapp.generated.resources.close
import salesautomator_test.composeapp.generated.resources.created_at_label
import salesautomator_test.composeapp.generated.resources.descending
import salesautomator_test.composeapp.generated.resources.description
import salesautomator_test.composeapp.generated.resources.empty
import salesautomator_test.composeapp.generated.resources.filter_list_24px
import salesautomator_test.composeapp.generated.resources.filters
import salesautomator_test.composeapp.generated.resources.home_screen_title
import salesautomator_test.composeapp.generated.resources.save_24px
import salesautomator_test.composeapp.generated.resources.sort
import salesautomator_test.composeapp.generated.resources.sort_24px
import salesautomator_test.composeapp.generated.resources.status
import salesautomator_test.composeapp.generated.resources.task_statuses
import salesautomator_test.composeapp.generated.resources.time_of_execution_label

object ScreenHome {


    @Composable
    operator fun invoke(
        onSelect: (Task?) -> Unit = {},
        onEdit: () -> Unit = {},
        vm: HomeScreenViewModel = viewModel { ViewModelModule.homeScreenVM },
        modifier: Modifier = Modifier.fillMaxSize()
    ) {

        val list by vm.taskList.collectAsState()
        var filterDialog by remember { mutableStateOf(false) }
        var sortDialog by remember { mutableStateOf(false) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {

            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
            ) {

                item(span = { GridItemSpan(maxLineSpan) }) {

                    TopAppBar(
                        title = { Text(stringResource(Res.string.home_screen_title)) },
                        actions = {
                            TextButton(
                                icon = painterResource(Res.drawable.add_24px),
                                text = stringResource(Res.string.add),
                                onClick = { onEdit() }
                            )
                            TextButton(
                                icon = painterResource(Res.drawable.filter_list_24px),
                                text = stringResource(Res.string.filters),
                                onClick = { filterDialog = true }
                            )
                            TextButton(
                                icon = painterResource(Res.drawable.sort_24px),
                                text = stringResource(Res.string.sort),
                                onClick = { sortDialog = true }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item(span = { GridItemSpan(maxLineSpan) }) { Divider(modifier = Modifier.fillMaxWidth()) }

                items(list.size) {

                    val task = list[it]

                    TaskCard(
                        task = task,
                        onClick = { onSelect(task) },
                        Modifier.padding(8.dp).fillMaxWidth()
                    )
                }

            }

            if (list.isEmpty()) Text(
                text = stringResource(Res.string.empty),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxSize()
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }

        AnimatedVisibility(
            visible = filterDialog,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            filterDialog(vm, onDispose = { filterDialog = false })
        }

        AnimatedVisibility(
            visible = sortDialog,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            sortDialog(vm, onDispose = { sortDialog = false })
        }


    }

    @Composable
    private fun sortDialog(
        vm: HomeScreenViewModel,
        onDispose: () -> Unit
    ) {
        Dialog(
            onDismissRequest = onDispose
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(200.dp)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(stringResource(Res.string.sort), style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.size(16.dp))
                    val order by vm.timeSort.collectAsState()
                    val sortType by vm.typeSort.collectAsState()
                    Row(
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextButton(
                                text = stringResource(Res.string.time_of_execution_label),
                                onClick = { vm.typeSort.value = SortType.EXECUTION_AT },
                                tintColor = if (sortType == SortType.EXECUTION_AT) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
                                backgroundColor = if (sortType == SortType.EXECUTION_AT) MaterialTheme.colors.primary else Color.Unspecified,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            TextButton(
                                text = stringResource(Res.string.created_at_label),
                                onClick = { vm.typeSort.value = SortType.CREATED_AT },
                                tintColor = if (sortType == SortType.CREATED_AT) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
                                backgroundColor = if (sortType == SortType.CREATED_AT) MaterialTheme.colors.primary else Color.Unspecified,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        Spacer(modifier = Modifier.size(8.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextButton(
                                text = stringResource(Res.string.ascending),
                                onClick = { vm.timeSort.value = TimeSort.ASC },
                                tintColor = if (order == TimeSort.ASC) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
                                backgroundColor = if (order == TimeSort.ASC) MaterialTheme.colors.primary else Color.Unspecified,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            TextButton(
                                text = stringResource(Res.string.descending),
                                onClick = { vm.timeSort.value = TimeSort.DESC },
                                tintColor = if (order == TimeSort.DESC) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface,
                                backgroundColor = if (order == TimeSort.DESC) MaterialTheme.colors.primary else Color.Unspecified,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    TextButton(
                        text = stringResource(Res.string.close),
                        onClick = onDispose,
                        modifier = Modifier.align(Alignment.End),
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }

    @Composable
    private fun filterDialog(
        vm: HomeScreenViewModel,
        onDispose: () -> Unit
    ) {
        val scope = rememberCoroutineScope()
        Dialog(
            onDismissRequest = onDispose
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(200.dp)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(stringResource(Res.string.filters), style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.size(16.dp))
                    val taskStatuses = stringArrayResource(Res.array.task_statuses)
                    val status by vm.statusFilter.collectAsState()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .clip(CircleShape)
                            .clickable {
                                if (!status.contains(TaskStatus.IN_PROGRESS)) vm.statusFilter.value += TaskStatus.IN_PROGRESS
                                else vm.statusFilter.value -= TaskStatus.IN_PROGRESS
                            }
                    ) {
                        RoundedCheckbox(
                            selected = status.contains(TaskStatus.IN_PROGRESS),
                            onClickListener = {
                                if (it) vm.statusFilter.value += TaskStatus.IN_PROGRESS
                                else vm.statusFilter.value -= TaskStatus.IN_PROGRESS
                            },
                            checkboxSize = 45.dp,
                            fillColor = MaterialTheme.colors.primary,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = taskStatuses[0], style = MaterialTheme.typography.button)
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .clip(CircleShape)
                            .clickable {
                                if (!status.contains(TaskStatus.CANCELED)) vm.statusFilter.value += TaskStatus.CANCELED
                                else vm.statusFilter.value -= TaskStatus.CANCELED
                            }
                    ) {
                        RoundedCheckbox(
                            selected = status.contains(TaskStatus.CANCELED),
                            onClickListener = {
                                if (it) vm.statusFilter.value += TaskStatus.CANCELED
                                else vm.statusFilter.value -= TaskStatus.CANCELED
                            },
                            checkboxSize = 45.dp,
                            fillColor = MaterialTheme.colors.primary,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = taskStatuses[2], style = MaterialTheme.typography.button)
                    }
                    Spacer(modifier = Modifier.size(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .clip(CircleShape)
                            .clickable {
                                if (!status.contains(TaskStatus.COMPLETED)) vm.statusFilter.value += TaskStatus.COMPLETED
                                else vm.statusFilter.value -= TaskStatus.COMPLETED
                            }
                    ) {
                        RoundedCheckbox(
                            selected = status.contains(TaskStatus.COMPLETED),
                            onClickListener = {
                                if (it) vm.statusFilter.value += TaskStatus.COMPLETED
                                else vm.statusFilter.value -= TaskStatus.COMPLETED
                            },
                            checkboxSize = 45.dp,
                            fillColor = MaterialTheme.colors.primary,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = taskStatuses[1], style = MaterialTheme.typography.button)
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                    TextButton(
                        text = stringResource(Res.string.close),
                        onClick = { scope.launch { onDispose() } },
                        modifier = Modifier.align(Alignment.End),
                        backgroundColor = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }

}