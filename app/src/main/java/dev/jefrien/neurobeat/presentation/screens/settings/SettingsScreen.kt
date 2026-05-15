package dev.jefrien.neurobeat.presentation.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jefrien.neurobeat.app.theme.LocalAppColors
import dev.jefrien.neurobeat.domain.model.ThemeMode
import dev.jefrien.neurobeat.presentation.common.utils.glassCard
import dev.jefrien.neurobeat.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val colors = LocalAppColors.current
    val themeMode by viewModel.themeMode.collectAsState()
    val accentColor by viewModel.accentColor.collectAsState()
    val cardOpacity by viewModel.cardOpacity.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colors.backgroundGradientStart, colors.backgroundGradientEnd)
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Settings",
                    color = colors.textPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                SettingsSection(title = "Theme") {
                    Text("Mode", color = colors.textSecondary, fontSize = 14.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ThemeMode.values().forEach { mode ->
                            val selected = themeMode == mode
                            Button(
                                onClick = { viewModel.setThemeMode(mode) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selected) colors.accent else colors.surfaceGlass,
                                    contentColor = if (selected) Color.White else colors.textPrimary
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(mode.name)
                            }
                        }
                    }
                }
            }

            item {
                SettingsSection(title = "Appearance") {
                    Text("Card Opacity", color = colors.textSecondary, fontSize = 14.sp)
                    Slider(
                        value = cardOpacity,
                        onValueChange = { viewModel.setCardOpacity(it) },
                        valueRange = 0.03f..0.4f,
                        colors = SliderDefaults.colors(
                            thumbColor = colors.accent,
                            activeTrackColor = colors.accent,
                            inactiveTrackColor = colors.divider
                        )
                    )
                }
            }

            item {
                SettingsSection(title = "Account") {
                    Button(
                        onClick = { viewModel.logout() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.error,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Logout")
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    val colors = LocalAppColors.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .glassCard(shape = RoundedCornerShape(20.dp), backgroundAlpha = 0.08f)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = title,
            color = colors.textPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        content()
    }
}
