package com.example.deolhonolixo.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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

        ReportCard("Eficiência da Frota", "88%", "Baseado em ${trucks.size} caminhões", Primary)
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard("Cobertura Urbana", "${routes.size} Rotas", "Total de bairros mapeados", Color(0xFF84CC16))

        Spacer(modifier = Modifier.height(24.dp))
        Text("Lista de Caminhões Registrados", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        
        trucks.forEach { truck ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(truck.licensePlate, color = Color.DarkGray)
                Text(truck.model, fontWeight = FontWeight.Bold, color = Primary)
            }
            HorizontalDivider(color = Color(0xFFF3F4F6))
        }
    }
}
