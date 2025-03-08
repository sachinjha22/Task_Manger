package com.example.taskmanger.data.local

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskmanger.data.model.AppTheme
import com.example.taskmanger.utils.fromJson
import com.example.taskmanger.utils.getValueFlow
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

// Define your DataStore
val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val dataStore = context.dataStore
    private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    private val PRIMARY_COLOR = stringPreferencesKey("primary_color")


    var isDarkMode: Boolean
        get() = runBlocking {
            withContext(Dispatchers.Default) {
                dataStore.getValueFlow(IS_DARK_MODE).first() ?: false
            }
        }
        set(value) = runBlocking {
            withContext(Dispatchers.Default) {
                dataStore.edit {
                    it[IS_DARK_MODE] = value
                }
            }
        }

    var primaryColor: String
        get() = runBlocking {
            withContext(Dispatchers.Default) {
                try {
                    Gson().fromJson(dataStore.getValueFlow(PRIMARY_COLOR).first())
                        ?: AppTheme.Default.name
                } catch (e: Exception) {
                    AppTheme.Default.name
                }
            }
        }
        set(value) = runBlocking {
            withContext(Dispatchers.Default) {
                dataStore.edit {
                    it[PRIMARY_COLOR] = value
                }
            }
        }
}
