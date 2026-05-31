package com.example.deolhonolixo.data.model

data class BairroInfo(
    val id: String = "",
    val displayNome: String = "",
    val periodo: String = "",
    val horarioPrevisto: String = "",
    val diasSemana: List<Int> = emptyList(), // 1 = Dom, 2 = Seg, ..., 7 = Sáb
    val horaInicio: Int = 0
)

data class UltimaColeta(val data: String)
