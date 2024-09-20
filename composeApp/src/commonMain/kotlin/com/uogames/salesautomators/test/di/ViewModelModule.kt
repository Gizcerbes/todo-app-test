package com.uogames.salesautomators.test.di

import com.uogames.salesautomators.test.ui.screen.add_task.AddTaskViewModel
import com.uogames.salesautomators.test.ui.screen.add_task.AddTaskViewModelImpl
import com.uogames.salesautomators.test.ui.screen.detail.DetailViewModel
import com.uogames.salesautomators.test.ui.screen.detail.DetailViewModelImpl
import com.uogames.salesautomators.test.ui.screen.home.HomeScreenViewModelImpl
import com.uogames.salesautomators.test.ui.screen.home.HomeScreenViewModel
import com.uogames.selesautomators.test.database.TaskRepository

object ViewModelModule {

    val homeScreenVM: HomeScreenViewModel
        get() = HomeScreenViewModelImpl(di.databaseRepository.taskRepository)

    val addTaskVM: AddTaskViewModel
        get() = AddTaskViewModelImpl(di.databaseRepository.taskRepository)

    val detailVM: DetailViewModel
        get() = DetailViewModelImpl(di.databaseRepository.taskRepository)

}