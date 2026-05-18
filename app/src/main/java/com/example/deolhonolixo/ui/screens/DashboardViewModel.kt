package com.example.deolhonolixo.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deolhonolixo.data.api.NetworkClient
import com.example.deolhonolixo.data.api.Truck
import com.example.deolhonolixo.data.api.TruckHistory
import com.example.deolhonolixo.data.api.UrbanGeometryResponse
import com.example.deolhonolixo.data.api.RouteResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val apiService = NetworkClient.apiService

    private val _trucks = MutableStateFlow<List<Truck>>(emptyList())
    val trucks: StateFlow<List<Truck>> = _trucks

    private val _urbanGeometry = MutableStateFlow<List<UrbanGeometryResponse>>(emptyList())
    val urbanGeometry: StateFlow<List<UrbanGeometryResponse>> = _urbanGeometry

    private val _routes = MutableStateFlow<List<RouteResponse>>(emptyList())
    val routes: StateFlow<List<RouteResponse>> = _routes

    private val _truckHistory = MutableStateFlow<List<TruckHistory>>(emptyList())
    val truckHistory: StateFlow<List<TruckHistory>> = _truckHistory

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            try {
                _trucks.value = apiService.getTrucks()
                _urbanGeometry.value = apiService.getUrbanGeometry()
                _routes.value = apiService.getRoutes()
                _truckHistory.value = apiService.getTrucksHistory()
            } catch (e: Exception) {
                // Em um app real, tratar erro (ex: StateFlow de erro)
                e.printStackTrace()
            }
        }
    }

    fun registerTruck(licensePlate: String, model: String) {
        viewModelScope.launch {
            try {
                apiService.registerTruck(Truck(licensePlate = licensePlate, model = model))
                _trucks.value = apiService.getTrucks() // Recarrega lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
