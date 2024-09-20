package com.uogames.salesautomators.test.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.RoomRawQuery
import androidx.room.Upsert
import com.uogames.salesautomators.test.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Upsert
    suspend fun save(vararg taskEntity: TaskEntity)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)

    @Query("SELECT * FROM task_table WHERE id = :id")
    suspend fun getByID(id: Int): TaskEntity?

    @Query("SELECT * FROM task_table")
    fun getAllFlow(): Flow<List<TaskEntity>>

    @RawQuery(observedEntities = [
        TaskEntity::class
    ])
    fun rawQuery(query: RoomRawQuery): Flow<List<TaskEntity>>


    fun getSortedListFlow(
        sortType: Int,
        timeSort: Int,
        statusFilter: List<Int>
    ): Flow<List<TaskEntity>> {
        val builder = StringBuilder()
        builder.append("SELECT * FROM task_table ")
        if (statusFilter.isNotEmpty()) {
            builder.append("WHERE status IN (${statusFilter.joinToString()}) ")
        }
        val sort = if (sortType == 0) "created_at" else "end_date_and_time"
        builder.append("ORDER BY $sort ${if (timeSort == 0) "ASC" else "DESC"}")
        return rawQuery(RoomRawQuery(builder.toString()))
    }

}