package com.uogames.salesautomators.test.database

import com.uogames.salesautomators.test.database.repository.TaskRepositoryImpl
import com.uogames.selesautomators.test.database.DatabaseRepository
import com.uogames.selesautomators.test.database.TaskRepository


class CommonDatabaseRepository(
    databaseTaskDatabase: TaskDatabase
): DatabaseRepository {

    override val taskRepository: TaskRepository = TaskRepositoryImpl(databaseTaskDatabase.taskDAO)

}


