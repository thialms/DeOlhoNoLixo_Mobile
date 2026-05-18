package com.example.deolhonolixo.data.model

data class BairroInfo(
    val id: String,
    val displayNome: String,
    val periodo: String,
    val horarioPrevisto: String,
    val diasSemana: List<Int>, // 1 = Dom, 2 = Seg, ..., 7 = Sáb
    val horaInicio: Int
)

data class UltimaColeta(val data: String)

val listaBairrosGeo = listOf(
    BairroInfo("militar", "Militar", "DIA", "07:00", listOf(1, 2, 3, 4, 5, 6, 7), 7),
    BairroInfo("canto-do-forte", "Canto do Forte", "NOITE", "18:00", listOf(2, 4, 6), 18),
    BairroInfo("boqueirao", "Boqueirão", "NOITE", "18:00", listOf(2, 4, 6), 18),
    BairroInfo("guilhermina", "Guilhermina", "NOITE", "18:00", listOf(2, 4, 6), 18),
    BairroInfo("aviacao", "Aviação", "NOITE", "18:00", listOf(2, 4, 6), 18),
    BairroInfo("tupi", "Tupi", "NOITE", "18:00", listOf(2, 4, 6), 18),
    BairroInfo("ocian", "Ocian", "NOITE", "18:00", listOf(3, 5, 1), 18),
    BairroInfo("mirim", "Mirim", "NOITE", "08:00", listOf(3, 5, 1), 8),
    BairroInfo("maracana", "Maracanã", "NOITE", "18:00", listOf(3, 5, 1), 18),
    BairroInfo("caicara", "Caiçara", "NOITE", "18:00", listOf(3, 5, 1), 18),
    BairroInfo("real", "Real", "NOITE", "18:00", listOf(3, 5, 1), 18),
    BairroInfo("florida", "Flórida", "DIA", "06:00", listOf(2, 4, 6), 6),
    BairroInfo("solemar", "Solemar", "DIA", "06:00", listOf(2, 4, 6), 6),
    BairroInfo("cidade-da-crianca", "Cidade da Criança", "DIA", "06:00", listOf(2, 4, 6), 6),
    BairroInfo("princesa", "Princesa", "DIA", "06:00", listOf(2, 4, 6), 6),
    BairroInfo("imperador", "Imperador", "DIA", "06:00", listOf(2, 4, 6), 6),
    BairroInfo("melvi", "Melvi", "DIA", "06:00", listOf(2, 4, 6), 6),
    BairroInfo("samambaia", "Samambaia", "DIA", "06:00", listOf(2, 4, 6), 6),
    BairroInfo("esmeralda", "Esmeralda", "DIA", "06:00", listOf(2, 4, 6), 6),
    BairroInfo("ribeiropolis", "Ribeirópolis", "DIA", "06:00", listOf(2, 4, 6), 6),
    BairroInfo("andaragua", "Andaraguá", "DIA", "07:00", listOf(1, 2, 3, 4, 5, 6, 7), 7),
    BairroInfo("nova-mirim", "Nova Mirim", "DIA", "06:00", listOf(3, 5, 7), 6),
    BairroInfo("anhanguera", "Anhanguera", "DIA", "06:00", listOf(3, 5, 7), 6),
    BairroInfo("quietude", "Quietude", "DIA", "06:00", listOf(3, 5, 7), 6),
    BairroInfo("santa-marina", "Santa Marina", "DIA", "06:00", listOf(3, 5, 7), 6),
    BairroInfo("tupiry", "Tupiry", "DIA", "06:00", listOf(3, 5, 7), 6),
    BairroInfo("antartica", "Antártica", "DIA", "06:00", listOf(3, 5, 7), 6),
    BairroInfo("vila-sonia", "Vila Sônia", "DIA", "06:00", listOf(3, 5, 7), 6),
    BairroInfo("gloria", "Glória", "DIA", "06:00", listOf(3, 5, 7), 6),
    BairroInfo("sitio-do-campo", "Sítio do Campo", "NOITE", "18:00", listOf(2, 4, 6), 18),
    BairroInfo("xixova", "Xixová", "DIA", "07:00", listOf(1, 2, 3, 4, 5, 6, 7), 7),
    BairroInfo("serra-do-mar", "Serra do Mar", "DIA", "07:00", listOf(1, 2, 3, 4, 5, 6, 7), 7)
)
