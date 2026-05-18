package com.example.deolhonolixo.data.model

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    val id: String,
    val name: String,
    val description: String? = null,
    val geometry: String? = null
)

data class UrbanGeometryResponse(
    val id: String,
    val name: String,
    val type: String,
    val geometry: String? = null
)

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
