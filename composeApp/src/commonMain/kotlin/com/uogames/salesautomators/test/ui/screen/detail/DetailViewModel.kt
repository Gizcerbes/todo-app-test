package com.uogames.salesautomators.test.ui.screen.detail

import androidx.lifecycle.ViewModel
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus

abstract class DetailViewModel: ViewModel() {


    abstract fun set(task: Task)

    abstract suspend fun changeStatus(status: TaskStatus)

    abstract suspend fun delete()

}