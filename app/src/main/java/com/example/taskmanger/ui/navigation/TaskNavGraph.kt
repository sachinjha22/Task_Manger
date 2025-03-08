package com.example.taskmanger.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.taskmanger.ui.screen.add.AddTaskScreen
import com.example.taskmanger.ui.screen.detail.TaskDetailsScreen
import com.example.taskmanger.ui.screen.home.HomeScreenNew
import com.example.taskmanger.ui.screen.setting.SettingScreen
import com.example.taskmanger.viewmodel.SettingViewModel
import com.example.taskmanger.viewmodel.TaskViewModel

@Composable
fun TaskNavHost(navController: NavHostController, viewModel: TaskViewModel,settingVM: SettingViewModel) {
    var fabClicked by remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreenNew(
                navController,
                fabClicked,
                { fabClicked = true },
                viewModel
            )
        }

        composable("addTask") {
            AddTaskScreen(navController, viewModel)
        }

        composable("taskDetails/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toIntOrNull()
            if (taskId != null) {
                TaskDetailsScreen(navController, viewModel, taskId)
            }
        }

        composable("setting") {
            SettingScreen(navController,settingVM)
        }

    }
}
