package dev.jefrien.neurobeat.app.navigation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jefrien.neurobeat.app.theme.LocalAppColors
import dev.jefrien.neurobeat.presentation.common.utils.glassBottomBar

@Composable
fun GlassBottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val colors = LocalAppColors.current

    val items = listOf(
        BottomNavItem(Screen.Discover.route, "Discover", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem(Screen.Search.route, "Search", Icons.Filled.Search, Icons.Outlined.Search),
        BottomNavItem(Screen.Library.route, "Library", Icons.Filled.LibraryMusic, Icons.Outlined.LibraryMusic),
        BottomNavItem(Screen.Settings.route, "Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .glassBottomBar()
            .navigationBarsPadding()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route
                BottomNavItemView(
                    item = item,
                    selected = selected,
                    onClick = { onNavigate(item.route) }
                )
            }
        }
    }
}

@Composable
private fun BottomNavItemView(
    item: BottomNavItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val colors = LocalAppColors.current
    val iconScale by animateFloatAsState(if (selected) 1.15f else 1f, label = "iconScale")
    val iconColor by animateColorAsState(
        if (selected) colors.accent else colors.textSecondary,
        label = "iconColor"
    )
    val textColor by animateColorAsState(
        if (selected) colors.accent else colors.textSecondary,
        label = "textColor"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .drawBehind {
                            drawCircle(
                                color = colors.accentGlow.copy(alpha = 0.3f),
                                radius = size.minDimension / 2f
                            )
                        }
                )
            }
            Icon(
                imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.label,
                tint = iconColor,
                modifier = Modifier.size(24.dp * iconScale)
            )
        }
        Text(
            text = item.label,
            color = textColor,
            fontSize = 10.sp,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

private data class BottomNavItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
