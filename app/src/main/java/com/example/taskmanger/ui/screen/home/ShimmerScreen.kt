package com.example.taskmanger.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerScreen(modifier: Modifier = Modifier) {
    val shimmerColor = Color.Gray.copy(alpha = 0.3f)
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(shimmerColor, shape = RoundedCornerShape(8.dp))
                .padding(20.dp)
        )
        Spacer(Modifier.height(8.dp))

    }
}