package com.uogames.salesautomators.test.database

import androidx.room.Room
import com.uogames.salesautomators.test.database.TaskDatabase.Companion.configure
import com.uogames.selesautomators.test.database.Database
import com.uogames.selesautomators.test.database.DatabaseRepository

fun Database.create(
    dbPath: String
): DatabaseRepository{
    val db = Room.databaseBuilder<TaskDatabase>(
        name = dbPath
    ).configure()
    return CommonDatabaseRepository(db)
}