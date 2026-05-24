package com.example.deolhonolixo.data.api

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String) {
        prefs.edit { putString("auth_token", token) }
    }

    fun fetchAuthToken(): String? {
        return prefs.getString("auth_token", null)
    }

    fun saveUserRoles(roles: List<String>) {
        prefs.edit { putStringSet("user_roles", roles.toSet()) }
    }

    fun fetchUserRoles(): Set<String>? {
        return prefs.getStringSet("user_roles", null)
    }

    fun isAdmin(): Boolean {
        return fetchUserRoles()?.contains("ROLE_ADMIN") == true
    }

    fun clearSession() {
        prefs.edit { clear() }
    }
}
