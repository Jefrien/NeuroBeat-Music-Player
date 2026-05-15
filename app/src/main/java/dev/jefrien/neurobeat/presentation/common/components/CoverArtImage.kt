package dev.jefrien.neurobeat.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import dev.jefrien.neurobeat.app.theme.LocalAppColors
import dev.jefrien.neurobeat.presentation.viewmodel.CoverArtViewModel

@Composable
fun CoverArtImage(
    coverArtId: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    viewModel: CoverArtViewModel = hiltViewModel()
) {
    val colors = LocalAppColors.current
    val url = viewModel.buildCoverArtUrl(coverArtId)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(colors.surfaceGlass),
        contentAlignment = Alignment.Center
    ) {
        if (url != null) {
            SubcomposeAsyncImage(
                model = url,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                loading = {
                    Box(modifier = Modifier.fillMaxSize().background(colors.surfaceGlass))
                },
                error = {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = null,
                        tint = colors.textTertiary,
                        modifier = Modifier.fillMaxSize(0.4f)
                    )
                }
            )
        } else {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = null,
                tint = colors.textTertiary,
                modifier = Modifier.fillMaxSize(0.4f)
            )
        }
    }
}
