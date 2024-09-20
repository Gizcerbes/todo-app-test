package com.uogames.salesautomators.test.di

import com.uogames.salesautomators.test.database.create
import com.uogames.selesautomators.test.database.Database
import com.uogames.selesautomators.test.database.DatabaseRepository
import platform.Foundation.NSHomeDirectory

actual val di: DIModule = IosDIModule()


class IosDIModule: DIModule(){

    override val databaseRepository: DatabaseRepository = Database.create(
        NSHomeDirectory() + "/$databaseName"
    )

}