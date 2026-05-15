package dev.jefrien.neurobeat.presentation.common.utils

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.jefrien.neurobeat.app.theme.LocalAppColors

fun Modifier.glassCard(
    shape: Shape = RoundedCornerShape(16.dp),
    borderWidth: Dp = 1.dp,
    backgroundAlpha: Float = 0.08f
): Modifier = composed {
    val colors = LocalAppColors.current
    this
        .clip(shape)
        .background(colors.surfaceGlass.copy(alpha = backgroundAlpha))
        .border(
            width = borderWidth,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.15f),
                    Color.Transparent
                )
            ),
            shape = shape
        )
}

fun Modifier.glassBottomBar(): Modifier = composed {
    val colors = LocalAppColors.current
    val context = LocalContext.current
    val canBlur = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    this
        .then(
            if (canBlur) {
                Modifier.graphicsLayer {
                    renderEffect = null // Blur se aplica con backdrop en composable
                }
            } else Modifier
        )
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    colors.surface.copy(alpha = 0.7f),
                    colors.surface.copy(alpha = 0.9f)
                )
            )
        )
        .drawBehind {
            drawLine(
                color = Color.White.copy(alpha = 0.1f),
                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                strokeWidth = 1f
            )
        }
}

fun Modifier.glassMiniPlayer(): Modifier = composed {
    val colors = LocalAppColors.current
    this
        .clip(RoundedCornerShape(24.dp))
        .background(colors.surface.copy(alpha = 0.85f))
        .border(
            width = 1.dp,
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.2f),
                    Color.Transparent
                )
            ),
            shape = RoundedCornerShape(24.dp)
        )
}
