package com.example.taskmanger.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmanger.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenNew(
    navController: NavController,
    fabClicked: Boolean,
    onFabClick: () -> Unit,
    viewModel: TaskViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                // adding Header here
                title = { Text("Your All Task", color = MaterialTheme.colorScheme.onBackground) },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("setting") }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background // Background color
                ),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(), // Ensures elevation when scrolled
//                modifier = Modifier.shadow(1.dp, spotColor = background), // Manually add elevation
                modifier = Modifier.shadow(
                    1.dp,
                    spotColor = MaterialTheme.colorScheme.outline
                ) // Adds elevation
            )
        },
        floatingActionButton = {  // Add new Task navigation click
            AnimatedVisibility(
                visible = true,
                enter = scaleIn(tween(300)) + fadeIn(),
                exit = scaleOut(tween(300)) + fadeOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        onFabClick()
                        navController.navigate("addTask")
                    },
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(5.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        }
    ) { padding ->
        // Showing your all task data list here
        TaskListScreen(modifier = Modifier.padding(padding), navController, viewModel)
    }
}

