package com.uogames.selesautomators.test.database

import com.uogames.selesautomators.test.common.model.SortType
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus
import com.uogames.selesautomators.test.common.model.TimeSort
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

interface TaskRepository {

    fun getListFlow(
        sortType: SortType,
        timeSort: TimeSort,
        statusFilter: List<TaskStatus>
    ): Flow<List<Task>>

    suspend fun getByID(id: Int): Task?

    suspend fun save(task: Task)

    suspend fun delete(task: Task)

    companion object{

        val default = TaskRepository.def
    }

}


val TaskRepository.Companion.def get() = object : TaskRepository{

    private val list = ArrayList<Task>()
    private val trigger = MutableStateFlow(0)

    override fun getListFlow(
        sortType: SortType,
        timeSort: TimeSort,
        statusFilter: List<TaskStatus>
    ): Flow<List<Task>> {
        return trigger.map { list }
    }

    override suspend fun save(task: Task) {
        list.add(task)
        trigger.value++
    }

    override suspend fun delete(task: Task) {

    }

    override suspend fun getByID(id: Int): Task {
        return Task.default
    }

}