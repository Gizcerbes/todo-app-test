package com.uogames.salesautomators.test.ui.screen.detail

import androidx.lifecycle.viewModelScope
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus
import com.uogames.selesautomators.test.database.TaskRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModelImpl(
    private val taskRepository: TaskRepository
) : DetailViewModel() {

    private var task: Task? = null

    private var saveJob: Job? = null
    private var deleteJob: Job? = null

    override fun set(task: Task) {
        this.task = task
    }

    override suspend fun changeStatus(status: TaskStatus) {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            task
                ?.copy(status = status)
                ?.also { taskRepository.save(it) }
        }
        saveJob?.join()
    }

    override suspend fun delete() {
        deleteJob?.cancel()
        deleteJob = viewModelScope.launch {
            task?.let { taskRepository.delete(it) }
        }
        deleteJob?.join()
    }
}