package com.example.deolhonolixo.data.model

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    val id: String,
    val name: String,
    val description: String? = null
)

data class UrbanGeometryResponse(
    val id: String,
    val name: String,
    val city: String? = null,
    val collectionPeriod: String? = null,
    val collectionTime: String? = null,
    val collectionDays: List<String>? = null
) {
    fun toBairroInfo(): BairroInfo {
        val hora = collectionTime?.split(":")?.firstOrNull()?.toIntOrNull() ?: 0
        val dias = collectionDays?.map { day ->
            when (day.lowercase()) {
                "domingo" -> 1
                "segunda", "segunda-feira" -> 2
                "terça", "terca", "terça-feira", "terca-feira" -> 3
                "quarta", "quarta-feira" -> 4
                "quinta", "quinta-feira" -> 5
                "sexta", "sexta-feira" -> 6
                "sábado", "sabado" -> 7
                else -> 0
            }
        }?.filter { it != 0 } ?: emptyList()

        return BairroInfo(
            id = name,
            displayNome = name.replaceFirstChar { it.uppercase() },
            periodo = collectionPeriod?.uppercase() ?: "N/A",
            horarioPrevisto = collectionTime ?: "N/A",
            diasSemana = dias,
            horaInicio = hora
        )
    }
}

data class Truck(
    val id: String? = null,
    @SerializedName("licensePlate") val licensePlate: String,
    val model: String,
    val capacity: Double? = null,
    val status: String? = null
)

data class Geolocation(
    val latitude: Double,
    val longitude: Double
)

data class TruckHistory(
    val id: String,
    val truckId: String,
    val location: Geolocation,
    val timestamp: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

// DTO corrigido para refletir exatamente o JSON: {"username": "...", "token": "..."}
data class LoginResponse(
    val username: String,
    val token: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val role: List<String>
)
