package com.example.taskmanger.utils

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.SoftwareKeyboardController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Modifier.hideKeyboardOnTap(
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?
): Modifier = this.then(
    Modifier.pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
            keyboardController?.hide()
        })
    }
)

inline fun <reified T> Gson.fromJson(json: String?): T? {
    return if (!json.isNullOrBlank()) {
        this.fromJson(json, object : TypeToken<T>() {}.type)
    } else {
        null
    }
}