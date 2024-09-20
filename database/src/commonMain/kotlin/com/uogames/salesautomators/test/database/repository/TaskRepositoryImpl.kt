package com.uogames.salesautomators.test.database.repository

import com.uogames.salesautomators.test.database.dao.TaskDAO
import com.uogames.salesautomators.test.database.mapper.TaskMapper.toEntity
import com.uogames.salesautomators.test.database.mapper.TaskMapper.toModel
import com.uogames.selesautomators.test.common.model.SortType
import com.uogames.selesautomators.test.common.model.Task
import com.uogames.selesautomators.test.common.model.TaskStatus
import com.uogames.selesautomators.test.common.model.TimeSort
import com.uogames.selesautomators.test.database.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val taskDAO: TaskDAO
) : TaskRepository {

    override fun getListFlow(
        type: SortType,
        timeSort: TimeSort,
        statusFilter: List<TaskStatus>
    ): Flow<List<Task>> {
        return taskDAO.getSortedListFlow(
            type.ordinal,
            timeSort.ordinal,
            statusFilter.map { it.ordinal }
        ).map { list -> list.map { it.toModel() } }
    }

    override suspend fun getByID(id: Int): Task? {
        return taskDAO.getByID(id)?.toModel()
    }

    override suspend fun save(task: Task) {
        taskDAO.save(task.toEntity())
    }

    override suspend fun delete(task: Task) {
        taskDAO.delete(task.toEntity())
    }
}