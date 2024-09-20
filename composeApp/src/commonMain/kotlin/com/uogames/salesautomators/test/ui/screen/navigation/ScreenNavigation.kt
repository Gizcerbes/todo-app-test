package com.uogames.salesautomators.test.ui.screen.navigation

import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uogames.salesautomators.test.ui.screen.add_task.ScreenAddTask
import com.uogames.salesautomators.test.ui.screen.detail.ScreenDetail
import com.uogames.salesautomators.test.ui.screen.home.ScreenHome

object ScreenNavigation {

    private object Destinations {
        const val HOME = "HOME"
        const val EDITOR = "EDITOR"
        const val DETAIL = "DETAIL"
    }


    @Composable
    operator fun invoke(
        vm: NavigationViewModel = viewModel { NavigationViewModel() },
        modifier: Modifier = Modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Destinations.HOME,
            modifier = modifier
        ) {
            composable(
                route = Destinations.HOME,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                ScreenHome(
                    onSelect = {
                        vm.taskKeeper.clear()
                        if (it != null) vm.taskKeeper[it.id] = it
                        navController.navigate("${Destinations.DETAIL}/${it?.id ?: -1}")
                    },
                    onEdit = {
                        navController.navigate("${Destinations.EDITOR}/${-1}")
                    }
                )
            }
            composable(
                route = "${Destinations.EDITOR}/{task_id}",
                arguments = listOf(
                    navArgument("task_id") { type = NavType.IntType }
                ),
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                val task = it.arguments
                    ?.getInt("task_id")
                    ?.let { id -> vm.taskKeeper.getOrElse(id) { null } }
                ScreenAddTask(
                    task,
                    onBack = {
                        vm.taskKeeper.remove(task?.id)
                        navController.popBackStack(
                            route = Destinations.HOME,
                            inclusive = false
                        )
                    }
                )
            }
            composable(
                route = "${Destinations.DETAIL}/{task_id}",
                arguments = listOf(
                    navArgument("task_id") { type = NavType.IntType }
                ),
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                val task = it.arguments
                    ?.getInt("task_id")
                    ?.let { id -> vm.taskKeeper.getOrElse(id) { null } }
                if (task == null) { navController.popBackStack(
                    route = Destinations.HOME,
                    inclusive = false
                )}
                else ScreenDetail(
                    task = task,
                    onBack = {
                        navController.popBackStack(
                            route = Destinations.HOME,
                            inclusive = false
                        )
                    },
                    onEdit = { t ->
                        vm.clear()
                        vm.taskKeeper[t.id] = t
                        navController.navigate("${Destinations.EDITOR}/${t.id}")
                    }
                )

            }
        }


    }

}