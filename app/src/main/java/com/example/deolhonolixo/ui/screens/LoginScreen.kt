package com.example.deolhonolixo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deolhonolixo.ui.components.CustomTextField
import com.example.deolhonolixo.ui.components.GoogleButton
import com.example.deolhonolixo.ui.components.PrimaryButton
import com.example.deolhonolixo.ui.components.SocialDivider
import com.example.deolhonolixo.ui.theme.Primary
import com.example.deolhonolixo.ui.theme.TextSecondary

@Composable
fun LoginScreen(
    onRegisterClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

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

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "seuemail@exemplo.com",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(24.dp))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Senha",
            placeholder = "••••••••••••",
            isPassword = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = "Esqueceu sua senha?",
                style = MaterialTheme.typography.labelLarge,
                color = Primary,
                modifier = Modifier.clickable { /* Handle forgot password */ }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Entrar",
            onClick = {
                isLoading = true
                onLoginSuccess()
            },
            isLoading = isLoading
        )

        SocialDivider()

        GoogleButton(onClick = { /* Handle Google Login */ })

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = buildAnnotatedString {
                append("Ainda não tem uma conta? ")
                withStyle(style = SpanStyle(color = Primary, fontWeight = FontWeight.Bold)) {
                    append("Faça o registro")
                }
            },
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable { onRegisterClick() }
        )
    }
}