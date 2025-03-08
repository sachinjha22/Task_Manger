package com.example.taskmanger.ui.screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.taskmanger.ui.component.TextFieldComponent
import com.example.taskmanger.ui.theme.TaskMangerTheme
import com.example.taskmanger.ui.theme.background
import com.example.taskmanger.ui.theme.onBackground
import com.example.taskmanger.viewmodel.TaskViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsScreen(
    navController: NavController,
    viewModel: TaskViewModel,
    taskId: Int
) {
    val task by viewModel.getTaskById(taskId).collectAsState(initial = null)
    var isRevealed by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // Start animation when the screen appears
    LaunchedEffect(Unit) {
        delay(100) // Small delay to allow Compose to settle
        isRevealed = true
    }

    AnimatedVisibility(
        visible = isRevealed,
        enter = scaleIn(
            initialScale = 0f
        ) + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        // adding Header here
                        title = {
                            Text(
                                "Task Detail",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background // Background color
                        ),
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(), // Ensures elevation when scrolled
                        modifier = Modifier.shadow(
                            1.dp,
                            spotColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }) { padding ->

                if (task != null) {
                    Box(modifier = Modifier.padding(padding)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 20.dp, end = 20.dp)
                                .verticalScroll(scrollState)
                                .align(alignment = Alignment.TopCenter)
                        ) {
                            TextFieldComponent(task?.title, "Title")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextFieldComponent(task?.priority?.name, "Priority")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextFieldComponent(task?.dueDate.toString(), "Due Date")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextFieldComponent(task?.description, "Description")
                            Spacer(modifier = Modifier.height(80.dp))
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(background)
                                    .padding(
                                        start = 10.dp,
                                        end = 10.dp,
                                        bottom = 10.dp,
                                        top = 10.dp
                                    ),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                if (!task?.isCompleted!!) {
                                    Button(
                                        onClick = {
                                            task?.let {
                                                viewModel.toggleComplete(it)
                                                navController.popBackStack()
                                            }
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .fillMaxWidth()
                                            .padding(end = 10.dp)
                                    ) {
                                        Text("Mark Completed")
                                    }
                                }

                                Button(
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                                    onClick = {
                                        task?.let {
                                            viewModel.deleteTask(it)
                                            navController.popBackStack()
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                        .padding(start = 10.dp)
                                ) {
                                    Text("Delete")
                                }
                            }
                        }
                    }
                }
            }

        }

    }
}


@Preview
@Composable
private fun Greet() {
    val nav = rememberNavController()
    TaskMangerTheme {
        TaskDetailsScreen(nav, hiltViewModel<TaskViewModel>(), 1)
    }
}