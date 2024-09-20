package com.uogames.salesautomators.test.ui.screen.home

import androidx.lifecycle.ViewModel
import com.uogames.selesautomators.test.common.model.SortType
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus
import com.uogames.selesautomators.test.common.model.TimeSort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class HomeScreenViewModel: ViewModel() {


    val statusFilter = MutableStateFlow<List<TaskStatus>>(emptyList())

    val typeSort = MutableStateFlow(SortType.EXECUTION_AT)

    val timeSort = MutableStateFlow(TimeSort.DESC)

    abstract val taskList: StateFlow<List<Task>>

}