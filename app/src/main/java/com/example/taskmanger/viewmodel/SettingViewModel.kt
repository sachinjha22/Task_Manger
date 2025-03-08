package com.example.taskmanger.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.example.taskmanger.data.local.DataStoreManager
import com.example.taskmanger.data.model.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val dataStoreManager: DataStoreManager) :
    ViewModel() {

    private val _isDarkTheme = MutableStateFlow(dataStoreManager.isDarkMode)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    private val _primaryColor = MutableStateFlow(dataStoreManager.primaryColor)
    val primaryColor: StateFlow<String> = _primaryColor

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
        dataStoreManager.isDarkMode = _isDarkTheme.value
    }

    fun setPrimaryColor(value: Int) {
        val color = if (value == Color.Green.toArgb()) {
            AppTheme.Green.name
        } else {
            AppTheme.Magenta.name
        }
        _primaryColor.value = color
        dataStoreManager.primaryColor = color
    }
}