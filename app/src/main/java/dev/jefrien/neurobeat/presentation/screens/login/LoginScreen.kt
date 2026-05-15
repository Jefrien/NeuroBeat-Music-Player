package dev.jefrien.neurobeat.presentation.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jefrien.neurobeat.app.theme.LocalAppColors
import dev.jefrien.neurobeat.presentation.common.utils.glassCard
import dev.jefrien.neurobeat.presentation.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val colors = LocalAppColors.current
    val state by viewModel.state.collectAsState()

    var serverUrl by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state is LoginViewModel.LoginState.Success) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(colors.backgroundGradientStart, colors.backgroundGradientEnd)
                )
            )
    ) {
        // Ambient glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    drawCircle(
                        color = colors.accent.copy(alpha = 0.08f),
                        radius = size.minDimension * 0.8f,
                        center = center.copy(y = center.y * 0.4f)
                    )
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = "Neurobeat",
                tint = colors.accent,
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = "Neurobeat",
                color = colors.textPrimary,
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Your music, your way",
                color = colors.textSecondary,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Glass card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .glassCard(shape = RoundedCornerShape(24.dp), backgroundAlpha = 0.1f)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Connect to Server",
                    color = colors.textPrimary,
                    fontSize = 20.sp
                )

                OutlinedTextField(
                    value = serverUrl,
                    onValueChange = { serverUrl = it },
                    label = { Text("Server URL", color = colors.textSecondary) },
                    placeholder = { Text("https://music.example.com", color = colors.textTertiary) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.accent,
                        unfocusedBorderColor = colors.divider,
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary,
                        focusedContainerColor = Color.White.copy(alpha = 0.05f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.03f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username", color = colors.textSecondary) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.accent,
                        unfocusedBorderColor = colors.divider,
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary,
                        focusedContainerColor = Color.White.copy(alpha = 0.05f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.03f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = colors.textSecondary) },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = colors.textSecondary
                            )
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colors.accent,
                        unfocusedBorderColor = colors.divider,
                        focusedTextColor = colors.textPrimary,
                        unfocusedTextColor = colors.textPrimary,
                        focusedContainerColor = Color.White.copy(alpha = 0.05f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.03f)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                AnimatedVisibility(visible = state is LoginViewModel.LoginState.Error) {
                    val error = (state as? LoginViewModel.LoginState.Error)?.message ?: ""
                    Text(
                        text = error,
                        color = colors.error,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Button(
                    onClick = {
                        if (serverUrl.isNotBlank() && username.isNotBlank() && password.isNotBlank()) {
                            viewModel.login(serverUrl, username, password)
                        }
                    },
                    enabled = state !is LoginViewModel.LoginState.Loading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.accent,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 8.dp)
                ) {
                    if (state is LoginViewModel.LoginState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Connect")
                    }
                }
            }
        }
    }
}
