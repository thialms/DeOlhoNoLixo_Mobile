package com.example.deolhonolixo.data.api

import com.example.deolhonolixo.data.model.*
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Unit

    @GET("routes")
    suspend fun getRoutes(): List<RouteResponse>

    @GET("routes/{id}")
    suspend fun getRouteById(@Path("id") id: String): RouteResponse

    @GET("urban-geometry")
    suspend fun getUrbanGeometry(): List<UrbanGeometryResponse>

    @GET("urban-geometry/{name}")
    suspend fun getUrbanGeometryByName(@Path("name") name: String): UrbanGeometryResponse

    @GET("trucks/history")
    suspend fun getTrucksHistory(): List<TruckHistory>

    @GET("trucks/history/{id}")
    suspend fun getTruckHistoryById(@Path("id") id: String): List<TruckHistory>

    @GET("trucks/geolocation/{id}")
    suspend fun getTruckGeolocation(@Path("id") id: String): Geolocation

    @POST("trucks/register")
    suspend fun registerTruck(@Body truck: Truck): Truck

    @GET("trucks")
    suspend fun getTrucks(): List<Truck>

    @GET("trucks/{licensePlate}")
    suspend fun getTruckByLicensePlate(@Path("licensePlate") licensePlate: String): Truck
}
