package com.uogames.salesautomators.test.ui.screen.add_task

import androidx.lifecycle.ViewModel
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.currentEpochMilliseconds
import com.uogames.selesautomators.test.common.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDateTime

abstract class AddTaskViewModel : ViewModel() {

    val title = MutableStateFlow("")
    abstract val titleError: StateFlow<String?>

    val description = MutableStateFlow("")

    val time = MutableStateFlow(LocalDateTime.currentEpochMilliseconds() + 1000 * 60)
    abstract val timeError: StateFlow<String?>

    val location = MutableStateFlow("")
    abstract val locationError: StateFlow<String?>

    abstract fun setTask(task: Task?)

    abstract suspend fun save(): Boolean

}