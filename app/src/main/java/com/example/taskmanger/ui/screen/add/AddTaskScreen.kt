package com.example.taskmanger.ui.screen.add

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmanger.data.model.Priority
import com.example.taskmanger.data.model.Task
import com.example.taskmanger.ui.theme.background
import com.example.taskmanger.ui.theme.onBackground
import com.example.taskmanger.viewmodel.TaskViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date

// Add new task screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    navController: NavController,
    viewModel: TaskViewModel
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(Priority.LOW.name) }
    var selectedDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    val scrollState = rememberScrollState()
    var isRevealed by remember { mutableStateOf(false) }

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
                .fillMaxSize()
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        // adding Header here
                        title = {
                            Text(
                                "New Task",
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
                },
                floatingActionButton = {  // adding Button for save
                    FloatingActionButton(
                        onClick = {
                            if (isValidate(
                                    title,
                                    description,
                                    selectedDate,
                                    context
                                )
                            ) {// validating data empty
                                viewModel.insertTask(
                                    Task(
                                        title = title,
                                        description = description,
                                        priority = Priority.valueOf(selectedPriority),
                                        dueDate = getStringToDate(selectedDate)
                                    )
                                )
                                Toast.makeText(context, "Task Added!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary,
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save Task")
                    }
                }
            ) { padding ->
                // screen body content
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .verticalScroll(scrollState)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Task Title
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = {
                                Text(
                                    "Task Title", color = MaterialTheme.colorScheme.onBackground
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Task Description
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = {
                                Text(
                                    "Task Description",
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                            textStyle = TextStyle(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Priority Selector
                        PriorityDropdown(selectedPriority) { selectedPriority = it }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Due Date Selector
                        DueDatePicker(selectedDate, context) { selectedDate = it }

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun getStringToDate(selectedDate: String): Date? {
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.parse(selectedDate)
}

fun isValidate(
    title: String,
    description: String,
    selectedDate: String,
    context: Context
): Boolean {
    if (title.isBlank()) {
        Toast.makeText(context, "Please Add Title!", Toast.LENGTH_SHORT).show()
        return false
    } else if (description.isBlank()) {
        Toast.makeText(context, "Please Add Description!", Toast.LENGTH_SHORT).show()
        return false
    } else if (selectedDate.isBlank()) {
        Toast.makeText(context, "Please Add Due Date!", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}
