package com.example.taskmanger.ui.screen.setting

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.taskmanger.data.model.AppTheme
import com.example.taskmanger.ui.theme.onBackground
import com.example.taskmanger.viewmodel.SettingViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController, viewModel: SettingViewModel) {
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()
    var isRevealed by remember { mutableStateOf(false) }
    val colors = listOf(Color.Green, Color.Magenta)
    val selectedColor by viewModel.primaryColor.collectAsState()


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
                        title = { Text("Setting", color = MaterialTheme.colorScheme.onBackground) },
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
                        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
                        modifier = Modifier.shadow(
                            1.dp,
                            spotColor = MaterialTheme.colorScheme.outline
                        )// Ensures elevation when scrolled

                    )
                }) { padding ->
                Column(modifier = Modifier.padding(padding)) {
                    Column(modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 30.dp)) {
                        Text("Settings", style = MaterialTheme.typography.titleLarge)

                        // Dark Mode Toggle
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Dark Mode", modifier = Modifier.weight(1f))
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { viewModel.toggleTheme() }
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Choose Primary Color", style = MaterialTheme.typography.titleMedium)

                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            colors.forEach { color ->
                                Spacer(Modifier.width(8.dp))
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(color, shape = CircleShape)
                                        .clickable { viewModel.setPrimaryColor(color.toArgb()) }
                                        .padding(8.dp)
                                        .border(
                                            width = if (isSelected(color.toArgb()) == selectedColor) 3.dp else 0.dp,
                                            color = Color.Black,
                                            shape = CircleShape
                                        )
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}

private fun isSelected(value: Int): String {
    val color = if (value == Color.Green.toArgb()) {
        AppTheme.Green.name
    } else {
        AppTheme.Magenta.name
    }
    return color
}