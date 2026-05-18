package com.example.deolhonolixo.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deolhonolixo.data.model.BairroInfo
import com.example.deolhonolixo.data.model.UltimaColeta
import com.example.deolhonolixo.data.model.listaBairrosGeo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class UserHomeViewModel : ViewModel() {

    var selectedBairro by mutableStateOf(listaBairrosGeo[7])
        private set

    var secondsRemaining by mutableLongStateOf(0L)
        private set

    var nextDate by mutableStateOf("")
        private set

    var ultimasColetas by mutableStateOf<List<UltimaColeta>>(emptyList())
        private set

    var expanded by mutableStateOf(false)

    init {
        updateBairro(selectedBairro)
    }

    fun updateBairro(bairro: BairroInfo) {
        selectedBairro = bairro
        val (segundos, data) = calcularProximaColeta(bairro)
        secondsRemaining = segundos
        nextDate = data
        ultimasColetas = gerarUltimasColetas(bairro)
        startCountdown()
    }

    private fun startCountdown() {
        viewModelScope.launch {
            while (secondsRemaining > 0) {
                delay(1000)
                secondsRemaining--
            }
        }
    }

    private fun calcularProximaColeta(bairro: BairroInfo): Pair<Long, String> {
        val agora = Calendar.getInstance()
        val proxima = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, bairro.horaInicio)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        if (!proxima.after(agora)) {
            proxima.add(Calendar.DAY_OF_YEAR, 1)
        }

        while (!bairro.diasSemana.contains(proxima.get(Calendar.DAY_OF_WEEK))) {
            proxima.add(Calendar.DAY_OF_YEAR, 1)
        }

        val segundos = (proxima.timeInMillis - agora.timeInMillis) / 1000
        val formatada = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(proxima.time)
        return Pair(segundos, formatada)
    }

    private fun gerarUltimasColetas(bairro: BairroInfo, quantidade: Int = 4): List<UltimaColeta> {
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

    fun getTimeDisplay(): String {
        val h = secondsRemaining / 3600
        val m = (secondsRemaining % 3600) / 60
        val s = secondsRemaining % 60
        return String.format(Locale.US, "%02d:%02d:%02d", h, m, s)
    }
}
