package com.example.taskmanger.ui.theme

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.taskmanger.data.model.AppTheme

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    primaryContainer = primaryContainer,
    background = background,
    onBackground = onBackground
)

private val LightColorScheme = lightColorScheme(
    primary = primaryNight,
    primaryContainer = primaryContainerNight,
    background = backgroundNight,
    onBackground = onBackgroundNight
)

private val GreenNightColorScheme = lightColorScheme(
    primary = primaryGreen,
    primaryContainer = primaryContainerNight,
    background = backgroundNight,
    onBackground = onBackgroundNight
)

private val GreenLightColorScheme = lightColorScheme(
    primary = primaryGreen,
    primaryContainer = primaryContainer,
    background = background,
    onBackground = onBackground
)

private val MagentaNightColorScheme = lightColorScheme(
    primary = primaryMagenta,
    primaryContainer = primaryContainerNight,
    background = backgroundNight,
    onBackground = onBackgroundNight
)

private val MagentaLightColorScheme = lightColorScheme(
    primary = primaryMagenta,
    primaryContainer = primaryContainer,
    background = background,
    onBackground = onBackground
)


val shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun TaskMangerTheme(
    appTheme: AppTheme = AppTheme.Default,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    Log.d("VVV", "appTheme $appTheme")
    val colorScheme = when (appTheme) {
        AppTheme.Default -> {
            when {
                darkTheme -> DarkColorScheme
                else -> LightColorScheme
            }
        }

        AppTheme.Magenta -> {
            when {
                darkTheme -> MagentaNightColorScheme
                else -> MagentaLightColorScheme
            }
        }

        AppTheme.Green -> {
            when {

                darkTheme -> GreenNightColorScheme
                else -> GreenLightColorScheme
            }
        }
    }


    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)

            // Set the system bar colors
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()

            // Apply dark/light mode based on theme
            insetsController.isAppearanceLightStatusBars = !darkTheme
            insetsController.isAppearanceLightNavigationBars = !darkTheme

            // Allow content to go under system bars
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        shapes = shapes,
        content = content
    )
}