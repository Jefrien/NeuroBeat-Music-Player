package dev.jefrien.neurobeat.presentation.screens.discover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jefrien.neurobeat.app.theme.LocalAppColors
import dev.jefrien.neurobeat.domain.model.Song
import dev.jefrien.neurobeat.presentation.common.components.CoverArtImage
import dev.jefrien.neurobeat.presentation.common.utils.glassCard
import dev.jefrien.neurobeat.presentation.viewmodel.DiscoverViewModel
import dev.jefrien.neurobeat.presentation.viewmodel.PlayerViewModel

@Composable
fun DiscoverScreen(
    viewModel: DiscoverViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val colors = LocalAppColors.current
    val state by viewModel.state.collectAsState()

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
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            item {
                Text(
                    text = "Create Your Best\nMusical Ideas With Us",
                    color = colors.textPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp)
                )
            }

            item {
                Text(
                    text = "Trending Songs",
                    color = colors.textPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 8.dp)
                )
            }

            item {
                when (val s = state) {
                    is DiscoverViewModel.DiscoverState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = colors.accent)
                        }
                    }
                    is DiscoverViewModel.DiscoverState.Error -> {
                        Text(
                            text = s.message,
                            color = colors.error,
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                    is DiscoverViewModel.DiscoverState.Success -> {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(s.randomSongs.take(10)) { song ->
                                TrendingSongCard(
                                    song = song,
                                    onClick = {
                                        playerViewModel.playQueue(s.randomSongs, s.randomSongs.indexOf(song))
                                    }
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Genres",
                    color = colors.textPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 8.dp)
                )
            }

            item {
                when (val s = state) {
                    is DiscoverViewModel.DiscoverState.Success -> {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(s.genres.take(8)) { genre ->
                                FilterChip(
                                    selected = false,
                                    onClick = { },
                                    label = { Text(genre.name) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        containerColor = colors.surfaceGlass,
                                        labelColor = colors.textPrimary
                                    )
                                )
                            }
                        }
                    }
                    else -> {}
                }
            }

            item {
                Text(
                    text = "Your Library",
                    color = colors.textPrimary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 8.dp)
                )
            }

            when (val s = state) {
                is DiscoverViewModel.DiscoverState.Success -> {
                    items(s.randomSongs) { song ->
                        SongListItem(
                            song = song,
                            onClick = {
                                playerViewModel.playQueue(s.randomSongs, s.randomSongs.indexOf(song))
                            }
                        )
                    }
                }
                else -> {}
            }
        }
    }
}

@Composable
private fun TrendingSongCard(song: Song, onClick: () -> Unit) {
    val colors = LocalAppColors.current
    Column(
        modifier = Modifier
            .width(140.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colors.surfaceGlass)
        ) {
            CoverArtImage(
                coverArtId = song.coverArtId,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.2f))
            )
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center)
                    .clip(RoundedCornerShape(50))
                    .background(colors.accent.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        Text(
            text = song.title,
            color = colors.textPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = song.artistName,
            color = colors.textSecondary,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun SongListItem(song: Song, onClick: () -> Unit) {
    val colors = LocalAppColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 6.dp, bottom = 6.dp)
            .glassCard(shape = RoundedCornerShape(12.dp), backgroundAlpha = 0.06f)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoverArtImage(
            coverArtId = song.coverArtId,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = song.title,
                color = colors.textPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${song.artistName} • ${song.albumName}",
                color = colors.textSecondary,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = formatDuration(song.duration),
            color = colors.textTertiary,
            fontSize = 12.sp
        )
    }
}

private fun formatDuration(seconds: Int): String {
    val m = seconds / 60
    val s = seconds % 60
    return "%d:%02d".format(m, s)
}
