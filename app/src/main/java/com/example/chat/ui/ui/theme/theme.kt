//package com.example.chat.ui.ui.theme
//
//import androidx.compose.foundation.isSystemInDarkTheme
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import com.example.chat.ui.ui.theme.AppTypography
//
//private val CustomLightColors = lightColorScheme(
//    primary = Color(0xFF388E3C), // Green
//    onPrimary = Color.White,
//    secondary = Color(0xFFFFC107), // Amber
//    onSecondary = Color.Black,
//    background = Color(0xFFF1F8E9), // Light green
//    onBackground = Color.Black,
//    surface = Color.White,
//    onSurface = Color.Black
//)
//
//private val CustomDarkColors = darkColorScheme(
//    primary = Color(0xFF81C784),
//    onPrimary = Color.Black,
//    secondary = Color(0xFFFFD54F),
//    onSecondary = Color.Black,
//    background = Color(0xFF121212),
//    onBackground = Color.White,
//    surface = Color(0xFF1E1E1E),
//    onSurface = Color.White
//)
//
//@Composable
//fun ChatAppTheme(
//    useDarkTheme: Boolean = isSystemInDarkTheme(),
//    content: @Composable () -> Unit
//) {
//    val colorScheme = if (useDarkTheme) CustomDarkColors else CustomLightColors
//
//    MaterialTheme(
//        colorScheme = colorScheme,
//        typography = AppTypography, // use custom typography
//        content = content
//    )
//}


package com.example.chat.ui.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightPinkColorScheme = lightColorScheme(
    primary = Color(0xFFE91E63),          // Pink - for buttons, headings
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFE3EC), // Light pink - card backgrounds, surfaces
    onPrimaryContainer = Color(0xFF880E4F),
    secondary = Color(0xFFF06292),        // Soft pink
    onSecondary = Color.White,
    background = Color(0xFFFFF8F9),       // Very light background
    surface = Color(0xFFFFF1F4),
    onSurface = Color(0xFF3D3D3D),
)

@Composable
fun ChatAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightPinkColorScheme,
        typography = Typography,
        shapes = Shapes(),
        content = content
    )
}
