package com.example.deolhonolixo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.deolhonolixo.ui.screens.DashboardScreen
import com.example.deolhonolixo.ui.screens.LoginScreen
import com.example.deolhonolixo.ui.screens.RegisterScreen
import com.example.deolhonolixo.ui.screens.UserHomeScreen
import com.example.deolhonolixo.ui.theme.DeOlhoNoLixoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeOlhoNoLixoTheme {
                var currentScreen by remember { mutableStateOf("user_home") }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        "user_home" -> UserHomeScreen(
                            onAdminClick = { currentScreen = "login" }
                        )
                        "login" -> LoginScreen(
                            onRegisterClick = { currentScreen = "register" },
                            onLoginSuccess = { email ->
                                if (email.contains("admin", ignoreCase = true)) {
                                    currentScreen = "dashboard"
                                } else {
                                    currentScreen = "user_home"
                                }
                            }
                        )
                        "register" -> RegisterScreen(
                            onLoginClick = { currentScreen = "login" },
                            onRegisterSuccess = { currentScreen = "login" }
                        )
                        "dashboard" -> DashboardScreen()
                    }
                }
            }
        }
    }
}
