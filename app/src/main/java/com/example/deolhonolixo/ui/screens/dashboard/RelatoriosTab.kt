package com.example.deolhonolixo.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deolhonolixo.data.model.RouteResponse
import com.example.deolhonolixo.data.model.Truck
import com.example.deolhonolixo.ui.theme.Primary

@Composable
fun RelatoriosTab(trucks: List<Truck>, routes: List<RouteResponse>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Análise de Dados", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))

        // Dados baseados no histórico (simulando cálculos de GPS)
        val avgSpeed = "32 km/h"
        val totalDistance = "124 km"
        val fuelEfficiency = "3.8 km/L"

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ReportCardSmall("Velocidade Média", avgSpeed, Modifier.weight(1f))
            ReportCardSmall("Distância Total", totalDistance, Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // ReportCard vem de DashboardComponents.kt para evitar duplicidade
        ReportCard("Consumo de Combustível", fuelEfficiency, "Média da frota em serviço", Color(0xFFEAB308))
        
        Spacer(modifier = Modifier.height(16.dp))

        ReportCard("Eficiência da Frota", "88%", "Baseado em ${trucks.size} caminhões", Primary)
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard("Cobertura Urbana", "${routes.size} Rotas", "Total de bairros mapeados", Color(0xFF84CC16))

        Spacer(modifier = Modifier.height(24.dp))
        Text("Lista de Caminhões Registrados", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        
        trucks.forEach { truck ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(truck.licensePlate, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("Status: ${truck.status ?: "Ativo"}", fontSize = 12.sp, color = Color.Gray)
                }
                Text(truck.model.ifEmpty { "Caminhão Padrão" }, color = Primary)
            }
            HorizontalDivider(color = Color(0xFFF3F4F6))
        }
    }
}

@Composable
fun ReportCardSmall(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontSize = 12.sp, color = Color.Gray)
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}
