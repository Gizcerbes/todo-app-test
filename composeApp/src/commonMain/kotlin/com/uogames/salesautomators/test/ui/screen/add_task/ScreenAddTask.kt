package com.uogames.salesautomators.test.ui.screen.add_task

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uogames.salesautomators.test.di.ViewModelModule
import com.uogames.salesautomators.test.ui.component.TextButton
import com.uogames.salesautomators.test.ui.component.time_carousel.TimeCarousel
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.currentEpochMilliseconds
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.toEpochMilliseconds
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.toLocalDateTime
import com.uogames.selesautomators.test.common.model.Task
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import salesautomator_test.composeapp.generated.resources.Res
import salesautomator_test.composeapp.generated.resources.arrow_back_24px
import salesautomator_test.composeapp.generated.resources.data_and_time_of_execution
import salesautomator_test.composeapp.generated.resources.description
import salesautomator_test.composeapp.generated.resources.location
import salesautomator_test.composeapp.generated.resources.save
import salesautomator_test.composeapp.generated.resources.save_24px
import salesautomator_test.composeapp.generated.resources.task_editor_title
import salesautomator_test.composeapp.generated.resources.title

object ScreenAddTask {


    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    operator fun invoke(
        task: Task? = null,
        vm: AddTaskViewModel = viewModel { ViewModelModule.addTaskVM },
        onBack: () -> Unit,
        modifier: Modifier = Modifier.fillMaxSize()
    ) {
        vm.setTask(task)
        val timeError by vm.timeError.collectAsState()
        val titleError by vm.titleError.collectAsState()
        val locationError by vm.locationError.collectAsState()
        val time by vm.time.collectAsState()
        val scope = rememberCoroutineScope()
        Column(
            modifier = modifier
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1)
            ) {

                item {

                    TopAppBar(
                        title = { Text(stringResource(Res.string.task_editor_title)) },
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
                                icon = painterResource(Res.drawable.save_24px),
                                text = stringResource(Res.string.save),
                                onClick = {
                                    scope.launch { if (vm.save()) onBack() }
                                }
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    )

                }

                item { Divider(modifier = Modifier.fillMaxWidth()) }


                item {
                    Text(
                        stringResource(Res.string.data_and_time_of_execution),
                        modifier = Modifier.padding(8.dp)
                    )
                }
                item { Divider(modifier = Modifier.fillMaxWidth()) }

                item {

                    TimeCarousel(
                        startTime = time.toLocalDateTime(TimeZone.currentSystemDefault()),
                        onTimeChanged = {
                            vm.time.value = it.toEpochMilliseconds(TimeZone.currentSystemDefault())
                        },
                        borderColor = if (timeError.isNullOrEmpty()) {
                            MaterialTheme.colors.onSurface.copy(
                                alpha = ContentAlpha.disabled
                            )
                        } else {
                            MaterialTheme.colors.error
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
                item { Divider(modifier = Modifier.fillMaxWidth()) }

                item {
                    val title by vm.title.collectAsState()
                    OutlinedTextField(
                        value = title,
                        onValueChange = { vm.title.value = it },
                        label = {
                            Text(stringResource(Res.string.title))
                        },
                        isError = !titleError.isNullOrEmpty(),
                        maxLines = 1,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                item {
                    val location by vm.location.collectAsState()

                    OutlinedTextField(
                        value = location,
                        onValueChange = { vm.location.value = it },
                        label = {
                            Text(stringResource(Res.string.location))
                        },
                        isError = !locationError.isNullOrEmpty(),
                        maxLines = 2,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                item {
                    val description by vm.description.collectAsState()
                    OutlinedTextField(
                        value = description,
                        onValueChange = { vm.description.value = it },
                        minLines = 4,
                        label = {
                            Text(stringResource(Res.string.description))
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }


            }
        }


    }


}