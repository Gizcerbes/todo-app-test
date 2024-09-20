package com.uogames.salesautomators.test.di

import com.uogames.salesautomators.test.database.create
import com.uogames.selesautomators.test.database.Database
import com.uogames.selesautomators.test.database.DatabaseRepository
import java.io.File

actual val di: DIModule = DesktopDIModule()

class DesktopDIModule: DIModule(){

    override val databaseRepository: DatabaseRepository = Database.create(
        File(databaseName).absolutePath
    )

}