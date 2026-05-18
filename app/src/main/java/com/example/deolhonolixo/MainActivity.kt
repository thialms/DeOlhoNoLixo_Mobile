package com.example.deolhonolixo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.deolhonolixo.ui.screens.*
import com.example.deolhonolixo.ui.theme.DeOlhoNoLixoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DeOlhoNoLixoTheme {
                var currentScreen by remember { mutableStateOf("user_home") }
                Surface(Modifier.fillMaxSize()) {
                    when (currentScreen) {
                        "user_home" -> UserHomeScreen { currentScreen = "login" }
                        "login" -> LoginScreen({ currentScreen = "register" }, { if (it.contains("admin")) currentScreen = "dashboard" else currentScreen = "user_home" })
                        "register" -> RegisterScreen({ currentScreen = "login" })
                        "dashboard" -> DashboardScreen()
                    }
                }
            }
        }
    }
}