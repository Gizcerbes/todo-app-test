package com.uogames.salesautomators.test.database

import android.content.Context
import androidx.room.Room
import com.uogames.salesautomators.test.database.TaskDatabase.Companion.configure
import com.uogames.selesautomators.test.database.Database
import com.uogames.selesautomators.test.database.DatabaseRepository

fun Database.create(
    dbPath: String,
    context: Context
): DatabaseRepository{
    val db = Room.databaseBuilder<TaskDatabase>(
        name = dbPath,
        context = context
    ).configure()
    return CommonDatabaseRepository(db)
}