package com.uogames.salesautomators.test.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.uogames.salesautomators.test.database.dao.TaskDAO
import com.uogames.salesautomators.test.database.entity.TaskEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


@ConstructedBy(TaskDatabaseConstructor::class)
@Database(
    version = 1,
    entities = [
        TaskEntity::class
    ]
)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDAO: TaskDAO

    companion object {

        fun RoomDatabase.Builder<TaskDatabase>.configure() : TaskDatabase {
            return setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        }

    }

}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object TaskDatabaseConstructor: RoomDatabaseConstructor<TaskDatabase>