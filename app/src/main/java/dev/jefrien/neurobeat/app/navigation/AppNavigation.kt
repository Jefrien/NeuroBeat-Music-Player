package dev.jefrien.neurobeat.app.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.jefrien.neurobeat.presentation.common.components.MiniPlayer
import dev.jefrien.neurobeat.presentation.screens.create.CreateScreen
import dev.jefrien.neurobeat.presentation.screens.discover.DiscoverScreen
import dev.jefrien.neurobeat.presentation.screens.library.LibraryScreen
import dev.jefrien.neurobeat.presentation.screens.login.LoginScreen
import dev.jefrien.neurobeat.presentation.screens.player.PlayerScreen
import dev.jefrien.neurobeat.presentation.screens.search.SearchScreen
import dev.jefrien.neurobeat.presentation.screens.settings.SettingsScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Discover.route,
        Screen.Search.route,
        Screen.Create.route,
        Screen.Library.route,
        Screen.Settings.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                Box {
                    GlassBottomBar(
                        currentRoute = currentRoute ?: Screen.Discover.route,
                        onNavigate = { route ->
                            navController.navigate(route) {
                                popUpTo(Screen.Discover.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = Screen.Login.route,
                modifier = Modifier.padding(
                    PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        bottom = if (showBottomBar) innerPadding.calculateBottomPadding() else innerPadding.calculateBottomPadding()
                    )
                )
            ) {
                composable(Screen.Login.route) {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate(Screen.Discover.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable(Screen.Discover.route) { DiscoverScreen() }
                composable(Screen.Search.route) { SearchScreen() }
                composable(Screen.Create.route) { CreateScreen() }
                composable(Screen.Library.route) { LibraryScreen() }
                composable(Screen.Settings.route) { SettingsScreen() }
                composable(Screen.Player.route) {
                    PlayerScreen(
                        onDismiss = { navController.popBackStack() }
                    )
                }
            }

            // MiniPlayer floating above content
            if (showBottomBar) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = innerPadding.calculateBottomPadding() + 72.dp)
                ) {
                    MiniPlayer(
                        onExpand = { navController.navigate(Screen.Player.route) }
                    )
                }
            }
        }
    }
}
