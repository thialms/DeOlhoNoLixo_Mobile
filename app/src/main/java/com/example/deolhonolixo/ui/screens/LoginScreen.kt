package com.example.deolhonolixo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deolhonolixo.data.api.SessionManager
import com.example.deolhonolixo.ui.components.CustomTextField
import com.example.deolhonolixo.ui.components.PrimaryButton
import com.example.deolhonolixo.ui.screens.login.LoginViewModel
import com.example.deolhonolixo.ui.screens.login.LoginViewModelFactory
import com.example.deolhonolixo.ui.theme.TextSecondary

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(sessionManager))
    
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = "Entre na\nsua conta",
            style = MaterialTheme.typography.headlineLarge,
            fontSize = 48.sp,
            lineHeight = 48.sp,
            letterSpacing = (-1).sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Digite seu e-mail e senha para fazer login",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Campo de Email vinculado ao estado do ViewModel
        CustomTextField(
            value = viewModel.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "Email",
            placeholder = "seuemail@exemplo.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Campo de Senha vinculado ao estado do ViewModel
        CustomTextField(
            value = viewModel.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "Senha",
            placeholder = "••••••••••••",
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Entrar",
            onClick = {
                viewModel.login(onLoginSuccess)
            },
            isLoading = isLoading
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
