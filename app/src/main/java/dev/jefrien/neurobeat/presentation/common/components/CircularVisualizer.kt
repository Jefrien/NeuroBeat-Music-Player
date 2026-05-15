package dev.jefrien.neurobeat.presentation.common.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    barCount: Int = 48,
    color: Color = Color.White,
    minBarHeightFraction: Float = 0.04f,
    maxBarHeightFraction: Float = 0.18f,
    barWidthFraction: Float = 0.018f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "visualizer")

    // Pre-calculate stable offsets so they don't change on recomposition
    val barOffsets = remember(barCount) {
        List(barCount) { index ->
            val pseudoRandom = ((index * 9301 + 49297) % 233280) / 233280f
            val duration = 450 + (pseudoRandom * 700).toInt()
            val delay = index * 35
            Pair(duration, delay)
        }
    }

    val animatedValues = List(barCount) { index ->
        val (duration, delay) = barOffsets[index]
        val anim by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = duration,
                    delayMillis = delay,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$index"
        )
        anim
    }

    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val maxRadius = size.minDimension / 2f
        val angleStep = 360f / barCount
        val minBarHeight = size.minDimension * minBarHeightFraction
        val maxBarHeight = size.minDimension * maxBarHeightFraction
        val barWidth = size.minDimension * barWidthFraction

        for (i in 0 until barCount) {
            val angle = i * angleStep
            val animValue = if (isPlaying) animatedValues[i] else 0.06f

            val barHeight = minBarHeight + (maxBarHeight - minBarHeight) * animValue

            // Draw bars growing outward from the edge
            val startRadius = maxRadius - barHeight
            val endRadius = maxRadius

            val startX = centerX + startRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val startY = centerY + startRadius * sin(Math.toRadians(angle.toDouble())).toFloat()
            val endX = centerX + endRadius * cos(Math.toRadians(angle.toDouble())).toFloat()
            val endY = centerY + endRadius * sin(Math.toRadians(angle.toDouble())).toFloat()

            val alpha = if (isPlaying) 0.5f + animValue * 0.5f else 0.2f

            drawLine(
                color = color.copy(alpha = alpha),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = barWidth,
                cap = StrokeCap.Round
            )
        }
    }
}
