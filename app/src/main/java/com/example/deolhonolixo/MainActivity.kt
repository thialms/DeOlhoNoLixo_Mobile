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
import com.example.deolhonolixo.data.api.NetworkClient
import com.example.deolhonolixo.data.api.SessionManager
import com.example.deolhonolixo.ui.screens.*
import com.example.deolhonolixo.ui.screens.login.LoginScreen
import com.example.deolhonolixo.ui.theme.DeOlhoNoLixoTheme

class MainActivity : ComponentActivity() {
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkClient.init(this)
        sessionManager = SessionManager(this)
        
        enableEdgeToEdge()
        setContent {
            DeOlhoNoLixoTheme {
                var currentScreen by remember { mutableStateOf("splash") }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        "splash" -> SplashScreen(
                            onTimeout = { currentScreen = "user_home" }
                        )
                        "user_home" -> UserHomeScreen(
                            onAdminClick = { 
                                // Agora sempre redireciona para o login ao clicar em entrar como administrador
                                currentScreen = "login"
                            }
                        )
                        "login" -> LoginScreen(
                            onLoginSuccess = { 
                                // Sucesso no login redireciona para dashboard
                                currentScreen = "dashboard"
                            }
                        )
                        "dashboard" -> {
                            DashboardScreen(onLogout = {
                                sessionManager.clearSession()
                                currentScreen = "user_home"
                            })
                        }
                    }
                }
            }
        }
    }
}
