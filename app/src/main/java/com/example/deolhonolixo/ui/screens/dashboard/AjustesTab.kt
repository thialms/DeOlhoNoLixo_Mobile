package com.example.deolhonolixo.ui.screens.dashboard

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Dns
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deolhonolixo.BuildConfig
import com.example.deolhonolixo.data.model.RegisterRequest
import com.example.deolhonolixo.ui.components.CustomTextField
import com.example.deolhonolixo.ui.screens.DashboardViewModel
import com.example.deolhonolixo.ui.theme.Primary

@Composable
fun AjustesTab(viewModel: DashboardViewModel, onLogout: () -> Unit) {
    val context = LocalContext.current
    val registerStatus by viewModel.registerStatus.collectAsState()

    LaunchedEffect(registerStatus) {
        registerStatus?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearRegisterStatus()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Gerenciamento do Sistema", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(24.dp))

        SettingSection(title = "Cadastrar Novo Administrador") {
            var username by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }

            Column(Modifier.padding(16.dp)) {
                CustomTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = "Username",
                    placeholder = "admin_exemplo"
                )
                Spacer(Modifier.height(8.dp))
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    placeholder = "admin@exemplo.com",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(Modifier.height(8.dp))
                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Senha",
                    placeholder = "••••••••",
                    isPassword = true
                )
                Spacer(Modifier.height(8.dp))
                CustomTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirmar Senha",
                    placeholder = "••••••••",
                    isPassword = true
                )
                
                Button(
                    onClick = {
                        if (password == confirmPassword && username.isNotEmpty() && email.isNotEmpty()) {
                            viewModel.registerAdmin(
                                RegisterRequest(
                                    username = username,
                                    email = email,
                                    password = password,
                                    confirmPassword = confirmPassword,
                                    role = listOf("ROLE_ADMIN")
                                )
                            )
                            username = ""; email = ""; password = ""; confirmPassword = ""
                        } else {
                            Toast.makeText(context, "Verifique os dados e a senha", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.PersonAdd, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Registrar Administrador")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Registrar Novo Caminhão", fontWeight = FontWeight.Bold, color = Primary)
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6)),
            shape = RoundedCornerShape(16.dp)
        ) {
            var plate by remember { mutableStateOf("") }
            val plateRegex = remember { Regex("^[A-Z]{3}[0-9][A-Z][0-9]{2}$") }

            Column(Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = plate,
                    onValueChange = { plate = it.uppercase() },
                    label = { Text("Placa (Ex: ABC1D23)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Button(
                    onClick = {
                        if (plate.matches(plateRegex)) {
                            viewModel.registerTruck(plate, "")
                            plate = ""
                        } else {
                            Toast.makeText(context, "Placa inválida! Use o padrão ABC1D23", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Registrar Caminhão")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        SettingSection(title = "Configurações de Rede") {
            SettingItem(
                icon = Icons.Default.Dns,
                title = "Endpoint API",
                value = BuildConfig.API_URL
            )
            SettingItem(
                icon = Icons.Default.CloudSync,
                title = "Sync automático",
                value = "Ativado"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red)
        ) {
            Icon(Icons.Default.Logout, null)
            Spacer(Modifier.width(8.dp))
            Text("Sair da Conta")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SettingSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun SettingItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Icon(icon, null, tint = Primary)
            Spacer(Modifier.width(12.dp))
            Text(title, fontWeight = FontWeight.Medium)
        }
        Text(value, color = Color.Gray)
    }
}
