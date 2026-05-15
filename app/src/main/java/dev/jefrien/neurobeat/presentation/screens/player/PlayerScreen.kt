package dev.jefrien.neurobeat.presentation.screens.player

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import dev.jefrien.neurobeat.app.theme.LocalAppColors
import dev.jefrien.neurobeat.presentation.common.components.CoverArtImage
import dev.jefrien.neurobeat.presentation.viewmodel.PlayerViewModel
import com.miller198.audiovisualizer.configs.ClippingRadiusConfig
import com.miller198.audiovisualizer.configs.GradientConfig
import com.miller198.audiovisualizer.soundeffect.SoundEffect
import com.miller198.audiovisualizer.ui.CircleVisualizer
import com.miller198.audiovisualizer.configs.VisualizerConfig
import ir.mahozad.multiplatform.wavyslider.material3.WavySlider

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    onDismiss: () -> Unit,
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val colors = LocalAppColors.current
    val state by viewModel.state.collectAsState()
    val song = state.currentSong ?: return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colors.backgroundGradientStart, colors.backgroundGradientEnd)
                )
            )
    ) {
        // Dark overlay on gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Close",
                        tint = colors.textPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Text(
                    text = "Now Playing",
                    color = colors.textPrimary,
                    fontSize = 16.sp
                )
                Box(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Circular cover art with real audio visualizer around it
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                // Real audio visualizer from ComposeCircleAudioVisualizer library
                if (state.audioSessionId != 0) {
                    CircleVisualizer(
                        audioSessionId = state.audioSessionId,
                        soundEffects = SoundEffect.WaveStroke,
                        visualizerConfig = VisualizerConfig.FftCaptureConfig.Default,
                        modifier = Modifier.fillMaxSize(1.35f),
                        color = colors.accent,
                        clippingRadiusConfig = ClippingRadiusConfig.Ratio(0.42f),
                        gradientConfig = GradientConfig.Disabled
                    )
                }

                // Circular cover art
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.60f)
                        .clip(CircleShape)
                        .background(colors.surfaceGlass)
                ) {
                    CoverArtImage(
                        coverArtId = song.coverArtId,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Song info
            Text(
                text = song.title,
                color = colors.textPrimary,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = song.artistName,
                color = colors.textSecondary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
            Text(
                text = song.genre ?: "",
                color = colors.accent,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Progress slider
            if (state.duration > 0) {
                val progress = state.currentPosition.toFloat() / state.duration.toFloat()
                WavySlider(
                    value = progress.coerceIn(0f, 1f),
                    onValueChange = {
                        val newPosition = (it * state.duration).toLong()
                        viewModel.seekTo(newPosition)
                    },
                    waveHeight = 10.dp,
                    waveVelocity = 6.dp to ir.mahozad.multiplatform.wavyslider.WaveDirection.HEAD,
                    colors = SliderDefaults.colors(
                        thumbColor = colors.accent,
                        activeTrackColor = colors.accent,
                        inactiveTrackColor = colors.divider
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    thumb = {
                        GlowingThumb(color = colors.accent)
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(state.currentPosition),
                        color = colors.textSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = formatTime(state.duration),
                        color = colors.textSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.toggleShuffle() }) {
                    Icon(
                        imageVector = Icons.Default.Shuffle,
                        contentDescription = "Shuffle",
                        tint = if (state.isShuffleOn) colors.accent else colors.textSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                IconButton(onClick = { viewModel.previous() }) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Previous",
                        tint = colors.textPrimary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(colors.accent)
                        .clickable { viewModel.playPause() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (state.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (state.isPlaying) "Pause" else "Play",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }

                IconButton(onClick = { viewModel.next() }) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Next",
                        tint = colors.textPrimary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                IconButton(onClick = { viewModel.toggleRepeat() }) {
                    Icon(
                        imageVector = when (state.repeatMode) {
                            Player.REPEAT_MODE_ONE -> Icons.Default.RepeatOne
                            else -> Icons.Default.Repeat
                        },
                        contentDescription = "Repeat",
                        tint = if (state.repeatMode != Player.REPEAT_MODE_OFF) colors.accent else colors.textSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

private fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val m = totalSeconds / 60
    val s = totalSeconds % 60
    return "%d:%02d".format(m, s)
}

@Composable
private fun GlowingThumb(color: Color) {
    Box(
        modifier = Modifier.size(20.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        // Outer soft glow
        Box(
            modifier = Modifier
                .size(20.dp)
                .drawBehind {
                    drawCircle(
                        brush = ShaderBrush(
                            RadialGradientShader(
                                center = Offset(size.width / 2, size.height / 2),
                                radius = size.width / 2,
                                colors = listOf(color.copy(alpha = 0.35f), color.copy(alpha = 0f))
                            )
                        )
                    )
                }
        )
        // Middle glow ring
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(color.copy(alpha = 0.6f), CircleShape)
        )
        // Bright inner core
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(Color.White, CircleShape)
        )
        // Tiny sparkle dot
        Box(
            modifier = Modifier
                .size(3.dp)
                .background(Color.White.copy(alpha = 0.9f), CircleShape)
        )
    }
}
