package com.uogames.salesautomators.test.di

import com.uogames.salesautomators.test.Application
import com.uogames.salesautomators.test.database.create
import com.uogames.selesautomators.test.database.Database
import com.uogames.selesautomators.test.database.DatabaseRepository

actual val di: DIModule = AndroidDIModule()


class AndroidDIModule: DIModule(){


    override val databaseRepository: DatabaseRepository = Database.create(
        Application.appContext.getDatabasePath(databaseName).absolutePath,
        Application.appContext
    )

}