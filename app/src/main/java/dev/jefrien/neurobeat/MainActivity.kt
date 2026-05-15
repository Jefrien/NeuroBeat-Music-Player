package dev.jefrien.neurobeat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import dev.jefrien.neurobeat.app.navigation.AppNavigation
import dev.jefrien.neurobeat.app.theme.LocalAppColors
import dev.jefrien.neurobeat.app.theme.NeurobeatTheme
import dev.jefrien.neurobeat.app.theme.ThemeManager
import dev.jefrien.neurobeat.app.theme.darkAppColors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themeManager: ThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NeurobeatTheme(themeManager = themeManager) {
                val colors = LocalAppColors.current
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.backgroundGradientStart)
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
