package com.example.deolhonolixo.data.api

import retrofit2.http.*

data class RouteResponse(
    val id: String,
    val name: String,
    val description: String? = null,
    val geometry: String? = null // GeoJSON or polyline
)

data class UrbanGeometryResponse(
    val id: String,
    val name: String,
    val type: String, // "Bairro" or "Rua"
    val geometry: String? = null // GeoJSON
)

data class Truck(
    val id: String? = null,
    val licensePlate: String,
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

interface ApiService {
    // Rotas
    @GET("routes")
    suspend fun getRoutes(): List<RouteResponse>

    @GET("routes/{id}")
    suspend fun getRouteById(@Path("id") id: String): RouteResponse

    // Urban Geometry
    @GET("urban-geometry")
    suspend fun getUrbanGeometry(): List<UrbanGeometryResponse>

    @GET("urban-geometry/{name}")
    suspend fun getUrbanGeometryByName(@Path("name") name: String): UrbanGeometryResponse

    // Histórico de caminhões
    @GET("trucks/history")
    suspend fun getTrucksHistory(): List<TruckHistory>

    @GET("trucks/history/{id}")
    suspend fun getTruckHistoryById(@Path("id") id: String): List<TruckHistory>

    @GET("trucks/geolocation/{id}")
    suspend fun getTruckGeolocation(@Path("id") id: String): Geolocation

    // Caminhões
    @POST("trucks/register")
    suspend fun registerTruck(@Body truck: Truck): Truck

    @GET("trucks")
    suspend fun getTrucks(): List<Truck>

    @GET("trucks/{licensePlate}")
    suspend fun getTruckByLicensePlate(@Path("licensePlate") licensePlate: String): Truck
}
