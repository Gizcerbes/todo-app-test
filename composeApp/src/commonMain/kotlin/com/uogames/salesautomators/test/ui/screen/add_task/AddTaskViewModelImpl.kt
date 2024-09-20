package com.uogames.salesautomators.test.ui.screen.add_task

import androidx.lifecycle.viewModelScope
import com.uogames.salesautomators.test.util.LocalDateTimeExchange.currentEpochMilliseconds
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus
import com.uogames.selesautomators.test.database.TaskRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.getStringArray
import salesautomator_test.composeapp.generated.resources.Res
import salesautomator_test.composeapp.generated.resources.add_task_errors

class AddTaskViewModelImpl(
    private val taskRepository: TaskRepository
) : AddTaskViewModel() {

    override val titleError: StateFlow<String?> = title.map {
        val it = it.trim()
        val errorArray = getStringArray(Res.array.add_task_errors)
        if (!(1..15).contains(it.length)) errorArray[0]
        else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    override val timeError: StateFlow<String?> = time.map {
        val errorArray = getStringArray(Res.array.add_task_errors)
        if (it < LocalDateTime.currentEpochMilliseconds()) errorArray[2]
        else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    override val locationError: StateFlow<String?> = location.map {
        val it = it.trim()
        val errorArray = getStringArray(Res.array.add_task_errors)
        if (!(1..90).contains(it.length)) errorArray[1]
        else null
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private var loadedTask: Task? = null

    private var saveJob: Job? = null

    override fun setTask(task: Task?) {
        loadedTask = task
        when (task) {
            null -> {
                title.value = ""
                description.value = ""
                time.value = LocalDateTime.currentEpochMilliseconds()
                location.value = ""
            }
            else -> {
                title.value = task.title
                description.value = task.description
                time.value = task.endDateAndTimeUTC
                location.value = task.location
            }
        }

    }

    override suspend fun save(): Boolean {
        if (!titleError.value.isNullOrEmpty()) return false
        if (!timeError.value.isNullOrEmpty()) return false
        if (!locationError.value.isNullOrEmpty()) return false
        saveJob = viewModelScope.launch {
            taskRepository.save(
                Task(
                    id = loadedTask?.id ?: 0,
                    title = title.value,
                    description = description.value,
                    endDateAndTimeUTC = time.value,
                    location = location.value,
                    status = TaskStatus.IN_PROGRESS,
                    createdAt = loadedTask?.createdAt ?: LocalDateTime.currentEpochMilliseconds()
                )
            )
        }
        saveJob?.join()
        return true
    }


}