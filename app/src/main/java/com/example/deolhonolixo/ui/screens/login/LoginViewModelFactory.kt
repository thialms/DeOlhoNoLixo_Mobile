package com.example.deolhonolixo.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deolhonolixo.data.api.SessionManager
import com.example.deolhonolixo.data.repository.WasteRepository

class LoginViewModelFactory(private val sessionManager: SessionManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(WasteRepository(), sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
