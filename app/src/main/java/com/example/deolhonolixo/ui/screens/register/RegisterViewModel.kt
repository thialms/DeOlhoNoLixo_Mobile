package com.example.deolhonolixo.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
        private set

    fun onNameChange(newValue: String) { name = newValue }
    fun onEmailChange(newValue: String) { email = newValue }
    fun onBirthDateChange(newValue: String) { birthDate = newValue }
    fun onPasswordChange(newValue: String) { password = newValue }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            isLoading = true
            delay(1000) // Simulação de rede
            isLoading = false
            onSuccess()
        }
    }
}
