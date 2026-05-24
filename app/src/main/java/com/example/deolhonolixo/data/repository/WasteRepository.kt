package com.example.deolhonolixo.data.repository

import com.example.deolhonolixo.data.api.ApiService
import com.example.deolhonolixo.data.api.NetworkClient
import com.example.deolhonolixo.data.model.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * WasteRepository centraliza o acesso aos dados e a lógica de negócio.
 * Segue o padrão Single Source of Truth (SSoT).
 */
class WasteRepository(private val apiService: ApiService = NetworkClient.apiService) {

    // Autenticação e Registro
    suspend fun login(request: LoginRequest): LoginResponse = apiService.login(request)
    
    suspend fun registerAdmin(request: RegisterRequest) = apiService.register(request)

    // Dados Remotos (API)
    suspend fun getTrucks(): List<Truck> = apiService.getTrucks()
    suspend fun getUrbanGeometry(): List<UrbanGeometryResponse> = apiService.getUrbanGeometry()
    suspend fun getRoutes(): List<RouteResponse> = apiService.getRoutes()
    suspend fun getTrucksHistory(): List<TruckHistory> = apiService.getTrucksHistory()
    suspend fun registerTruck(truck: Truck) = apiService.registerTruck(truck)

    // Dados Locais e Lógica de Domínio
    fun getBairros(): List<BairroInfo> = listaBairrosGeo

    fun calcularProximaColeta(bairro: BairroInfo): Pair<Long, String> {
        val agora = Calendar.getInstance()
        val proxima = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, bairro.horaInicio)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (!proxima.after(agora)) proxima.add(Calendar.DAY_OF_YEAR, 1)

        while (!bairro.diasSemana.contains(proxima.get(Calendar.DAY_OF_WEEK))) {
            proxima.add(Calendar.DAY_OF_YEAR, 1)
        }

        val segundos = (proxima.timeInMillis - agora.timeInMillis) / 1000
        val formatada = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(proxima.time)
        return Pair(segundos, formatada)
    }

    fun gerarUltimasColetas(bairro: BairroInfo, quantidade: Int = 4): List<UltimaColeta> {
        val ultimas = mutableListOf<UltimaColeta>()
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))

        var count = 0
        while (count < quantidade) {
            cal.add(Calendar.DAY_OF_YEAR, -1)
            if (bairro.diasSemana.contains(cal.get(Calendar.DAY_OF_WEEK))) {
                ultimas.add(UltimaColeta(sdf.format(cal.time)))
                count++
            }
        }
        return ultimas
    }
}
