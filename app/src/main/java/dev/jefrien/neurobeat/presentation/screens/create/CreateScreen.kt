package dev.jefrien.neurobeat.presentation.screens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jefrien.neurobeat.app.theme.LocalAppColors
import dev.jefrien.neurobeat.presentation.common.utils.glassCard

private val genres = listOf("Dramatic", "POP", "HIP-HOP", "Rock", "Trance", "Blues")

@Composable
fun CreateScreen() {
    val colors = LocalAppColors.current
    var selectedGenre by remember { mutableStateOf<String?>(null) }
    var musicLength by remember { mutableFloatStateOf(180f) } // 3 minutes default

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colors.backgroundGradientStart, colors.backgroundGradientEnd)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Generate Music",
                color = colors.textPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "Select Genre",
                color = colors.textPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.height(220.dp)
            ) {
                items(genres) { genre ->
                    val isSelected = selectedGenre == genre
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .glassCard(
                                shape = RoundedCornerShape(16.dp),
                                backgroundAlpha = if (isSelected) 0.2f else 0.08f
                            )
                            .background(
                                if (isSelected) colors.accent.copy(alpha = 0.2f) else Color.Transparent,
                                RoundedCornerShape(16.dp)
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = genre,
                            color = if (isSelected) colors.accent else colors.textPrimary,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Music Length",
                color = colors.textPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = formatTime(musicLength.toInt()),
                color = colors.accent,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Slider(
                value = musicLength,
                onValueChange = { musicLength = it },
                valueRange = 90f..300f,
                steps = 20,
                colors = SliderDefaults.colors(
                    thumbColor = colors.accent,
                    activeTrackColor = colors.accent,
                    inactiveTrackColor = colors.divider
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { /* TODO: generate */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.accent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("GENERATE", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun formatTime(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%02d:%02d".format(m, s)
}
