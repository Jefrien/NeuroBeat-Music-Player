package dev.jefrien.neurobeat.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jefrien.neurobeat.app.theme.LocalAppColors

@Composable
fun SearchScreen() {
    val colors = LocalAppColors.current
    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colors.backgroundGradientStart, colors.backgroundGradientEnd)
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Text(
                text = "Search",
                color = colors.textPrimary,
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Artists, songs, albums...", color = colors.textTertiary) },
                leadingIcon = { Icon(Icons.Default.Search, null, tint = colors.textSecondary) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.accent,
                    unfocusedBorderColor = colors.divider,
                    focusedTextColor = colors.textPrimary,
                    unfocusedTextColor = colors.textPrimary,
                    focusedContainerColor = Color.White.copy(alpha = 0.05f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.03f)
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
