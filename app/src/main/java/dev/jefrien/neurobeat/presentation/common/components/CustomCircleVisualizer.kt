package dev.jefrien.neurobeat.presentation.common.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.miller198.audiovisualizer.BaseVisualizer
import com.miller198.audiovisualizer.defaultPreProcessFftData
import com.miller198.audiovisualizer.configs.VisualizerCallbacks
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

private const val CAPTURE_SIZE = 1024
private const val MIN_FREQ = 40
private const val MAX_FREQ = 4000

@Composable
fun CustomCircleVisualizer(
    audioSessionId: Int,
    isPlaying: Boolean,
    modifier: Modifier = Modifier,
    barCount: Int = 64,
    color: Color = Color.White,
    minBarHeightPx: Float = 4f,
    maxBarHeightPx: Float = 120f,
    barWidthPx: Float = 10f,
    coverRadiusFraction: Float = 0.28f,
    animationDurationMs: Int = 100
) {
    val visualizer = remember { BaseVisualizer() }
    val magnitudes = remember { mutableStateOf(List(barCount) { 0.05f }) }

    // Animated magnitudes for smooth transitions
    val animatedMagnitudes = remember {
        mutableStateOf(List(barCount) { Animatable(0.05f) })
    }

    LaunchedEffect(audioSessionId, isPlaying) {
        if (!isPlaying || audioSessionId == 0) return@LaunchedEffect

        visualizer.start(
            audioSessionId = audioSessionId,
            captureSize = CAPTURE_SIZE,
            useWaveCapture = false,
            useFftCapture = true,
            visualizerCallbacks = VisualizerCallbacks(
                onFftCaptured = { _, bytes, samplingRate ->
                    val processed = defaultPreProcessFftData(
                        bytes,
                        CAPTURE_SIZE,
                        MIN_FREQ,
                        MAX_FREQ,
                        samplingRate
                    )
                    // Downsample to barCount
                    val downsampled = downsample(processed, barCount)
                    magnitudes.value = downsampled
                }
            )
        )
    }

    // Animate magnitude changes smoothly
    LaunchedEffect(magnitudes.value) {
        if (animatedMagnitudes.value.size != barCount) {
            animatedMagnitudes.value = List(barCount) { Animatable(0.05f) }
        }
        magnitudes.value.forEachIndexed { index, value ->
            launch {
                animatedMagnitudes.value.getOrNull(index)?.animateTo(
                    targetValue = value,
                    animationSpec = tween(
                        durationMillis = animationDurationMs,
                        easing = LinearEasing
                    )
                )
            }
        }
    }

    DisposableEffect(audioSessionId) {
        onDispose {
            visualizer.stop()
        }
    }

    Canvas(modifier = modifier) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val coverRadius = size.minDimension * coverRadiusFraction
        val angleStep = 360f / barCount
        val startAngleRad = Math.toRadians(-90.0)

        for (i in 0 until barCount) {
            val angle = Math.toRadians((i * angleStep).toDouble()) + startAngleRad
            val animValue = if (isPlaying) {
                animatedMagnitudes.value.getOrNull(i)?.value ?: 0.05f
            } else {
                0.05f
            }

            val barHeight = minBarHeightPx + (maxBarHeightPx - minBarHeightPx) * animValue

            // Bars grow outward from cover edge
            val startRadius = coverRadius
            val endRadius = coverRadius + barHeight

            val startX = centerX + startRadius * cos(angle).toFloat()
            val startY = centerY + startRadius * sin(angle).toFloat()
            val endX = centerX + endRadius * cos(angle).toFloat()
            val endY = centerY + endRadius * sin(angle).toFloat()

            val alpha = if (isPlaying) 0.7f + animValue * 0.3f else 0.3f

            drawLine(
                color = color.copy(alpha = alpha),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = barWidthPx,
                cap = StrokeCap.Round
            )
        }
    }
}

private fun downsample(data: List<Float>, targetSize: Int): List<Float> {
    if (data.isEmpty() || targetSize <= 0) return List(targetSize) { 0.05f }
    if (data.size <= targetSize) {
        // Pad with low values
        return data + List(targetSize - data.size) { 0.05f }
    }

    val result = mutableListOf<Float>()
    val bucketSize = data.size.toFloat() / targetSize

    for (i in 0 until targetSize) {
        val start = (i * bucketSize).toInt()
        val end = ((i + 1) * bucketSize).toInt().coerceAtMost(data.size)
        val avg = if (end > start) {
            data.subList(start, end).average().toFloat()
        } else {
            data.getOrNull(start) ?: 0.05f
        }
        result.add(avg.coerceIn(0f, 1f))
    }
    return result
}
