package com.example.taskmanger.ui.activity

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.taskmanger.data.model.AppTheme
import com.example.taskmanger.ui.navigation.TaskNavHost
import com.example.taskmanger.ui.theme.TaskMangerTheme
import com.example.taskmanger.utils.hideKeyboardOnTap
import com.example.taskmanger.viewmodel.SettingViewModel
import com.example.taskmanger.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

// Dashboard
@AndroidEntryPoint
class DBActivity : ComponentActivity() {

    private val taskManagerVM: TaskViewModel by viewModels()
    private val settingVM: SettingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val isDarkTheme by settingVM.isDarkTheme.collectAsState()
            val primaryColor by settingVM.primaryColor.collectAsState()

            TaskMangerTheme(darkTheme = isDarkTheme, appTheme = AppTheme.valueOf(primaryColor)) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .hideKeyboardOnTap(focusManager, keyboardController),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    TaskNavHost(navController, taskManagerVM, settingVM)
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    notificationPermissionRequest.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    private val notificationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
        }
    }
}