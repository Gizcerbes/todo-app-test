package com.uogames.salesautomators.test.ui.screen.home

import androidx.lifecycle.viewModelScope
import com.uogames.selesautomators.test.common.model.SortType
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus
import com.uogames.selesautomators.test.common.model.TimeSort
import com.uogames.selesautomators.test.database.TaskRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModelImpl(
    taskRepository: TaskRepository
) : HomeScreenViewModel() {

    data class Query(
        var statusFilters: List<TaskStatus> = emptyList(),
        var timeSort: TimeSort = TimeSort.DESC,
        var typeSort: SortType = SortType.EXECUTION_AT
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val taskList: StateFlow<List<Task>> = statusFilter
        .combine(typeSort) { f,s -> Query(statusFilters = f, typeSort = s)}
        .combine(timeSort) { q, time ->
            q.timeSort = time
            taskRepository.getListFlow(
                q.typeSort,
                q.timeSort,
                q.statusFilters
            )
        }.flatMapLatest { it }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())



}