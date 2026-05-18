package com.example.deolhonolixo.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deolhonolixo.data.model.*
import com.example.deolhonolixo.data.repository.WasteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsável pela lógica do Dashboard administrativo.
 */
class DashboardViewModel(
    private val repository: WasteRepository = WasteRepository()
) : ViewModel() {

    private val _trucks = MutableStateFlow<List<Truck>>(emptyList())
    val trucks: StateFlow<List<Truck>> = _trucks.asStateFlow()

    private val _urbanGeometry = MutableStateFlow<List<UrbanGeometryResponse>>(emptyList())
    val urbanGeometry: StateFlow<List<UrbanGeometryResponse>> = _urbanGeometry.asStateFlow()

    private val _routes = MutableStateFlow<List<RouteResponse>>(emptyList())
    val routes: StateFlow<List<RouteResponse>> = _routes.asStateFlow()

    private val _truckHistory = MutableStateFlow<List<TruckHistory>>(emptyList())
    val truckHistory: StateFlow<List<TruckHistory>> = _truckHistory.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            try {
                _trucks.value = repository.getTrucks()
                _urbanGeometry.value = repository.getUrbanGeometry()
                _routes.value = repository.getRoutes()
                _truckHistory.value = repository.getTrucksHistory()
            } catch (e: Exception) {
                // Log de erro profissional
                e.printStackTrace()
            }
        }
    }

    fun registerTruck(licensePlate: String, model: String) {
        viewModelScope.launch {
            try {
                repository.registerTruck(Truck(licensePlate = licensePlate, model = model))
                loadDashboardData() // Refresh
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
