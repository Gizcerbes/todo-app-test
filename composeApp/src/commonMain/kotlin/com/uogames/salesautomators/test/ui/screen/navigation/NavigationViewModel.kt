package com.uogames.salesautomators.test.ui.screen.navigation

import androidx.lifecycle.ViewModel
import com.uogames.selesautomators.test.common.model.Task
import kotlinx.coroutines.flow.MutableStateFlow

class NavigationViewModel: ViewModel() {


    //val taskKeeper = MutableStateFlow<Task?>(null)

    val taskKeeper: HashMap<Int, Task> = HashMap()


    fun clear(){
        taskKeeper.clear()
    }

}