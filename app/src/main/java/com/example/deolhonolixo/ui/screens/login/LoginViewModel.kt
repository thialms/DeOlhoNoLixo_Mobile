package com.example.deolhonolixo.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deolhonolixo.data.api.SessionManager
import com.example.deolhonolixo.data.model.LoginRequest
import com.example.deolhonolixo.data.repository.WasteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import android.util.Log

class LoginViewModel(
    private val repository: WasteRepository = WasteRepository(),
    private val sessionManager: SessionManager
) : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun onEmailChange(newValue: String) {
        email = newValue
    }

    fun onPasswordChange(newValue: String) {
        password = newValue
    }

    fun login(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            _error.value = "Por favor, preencha todos os campos."
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                Log.d("LoginViewModel", "Iniciando login para: $email")
                val response = repository.login(LoginRequest(email, password))
                Log.d("LoginViewModel", "Login bem-sucedido. Usuário: ${response.username}")
                
                sessionManager.saveAuthToken(response.token)
                
                // Nota: O endpoint de login atual não retorna roles. 
                // A role ROLE_ADMIN deve ser verificada através de outro mecanismo ou endpoint 
                // se necessário, mas para evitar o erro "non-null is null", removemos o acesso
                // direto a campos não presentes no JSON.
                
                onSuccess()
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Erro na autenticação", e)
                _error.value = "Falha na autenticação: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
