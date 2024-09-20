package com.uogames.salesautomators.test.di

import com.uogames.selesautomators.test.database.DatabaseRepository


expect val di: DIModule


abstract class DIModule {

    val databaseName = "task.db"

    abstract val databaseRepository: DatabaseRepository
}