package dev.jefrien.neurobeat.presentation.screens.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jefrien.neurobeat.app.theme.LocalAppColors

@Composable
fun LibraryScreen() {
    val colors = LocalAppColors.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colors.backgroundGradientStart, colors.backgroundGradientEnd)
                )
            )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Library",
                color = colors.textPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Coming soon...",
                color = colors.textSecondary,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
