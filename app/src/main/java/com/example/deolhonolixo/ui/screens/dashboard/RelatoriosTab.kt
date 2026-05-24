package com.example.deolhonolixo.ui.screens.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
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
        Text("Dashboard Operacional", fontWeight = FontWeight.Bold, fontSize = 26.sp)
        Text("Dados em tempo real baseados no GPS", fontSize = 14.sp, color = Color.Gray)
        
        Spacer(modifier = Modifier.height(24.dp))

        // Gráfico de Velocidade Média (Linha)
        Text("Velocidade Média (Últimas 6h)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Card(
            Modifier.fillMaxWidth().height(200.dp).padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            LineChart(
                data = listOf(20f, 35f, 25f, 40f, 32f, 38f),
                modifier = Modifier.fillMaxSize().padding(16.dp),
                color = Primary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cards de Resumo
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ReportCardSmall("Consumo Médio", "3.8 km/L", Modifier.weight(1f))
            ReportCardSmall("Tempo em Rota", "5h 42min", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Gráfico de Volume de Coleta (Barras)
        Text("Volume de Coleta por Turno", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Card(
            Modifier.fillMaxWidth().height(200.dp).padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            BarChart(
                data = listOf(0.4f, 0.9f, 0.7f, 0.5f),
                labels = listOf("Manhã", "Tarde", "Noite", "Madru."),
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        
        // Status da Frota - Usando o ReportCard de DashboardComponents.kt
        ReportCard(
            title = "Eficiência Global", 
            value = "92%", 
            description = "${trucks.size} caminhões operantes agora",
            color = Color(0xFF10B981)
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun LineChart(data: List<Float>, modifier: Modifier, color: Color) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val spacing = width / (data.size - 1)
        val maxVal = data.maxOrNull() ?: 1f
        
        val path = Path().apply {
            data.forEachIndexed { index, value ->
                val x = index * spacing
                val y = height - (value / (maxVal * 1.2f) * height)
                if (index == 0) moveTo(x, y) else lineTo(x, y)
            }
        }
        drawPath(path, color, style = Stroke(width = 3.dp.toPx()))
    }
}

@Composable
fun BarChart(data: List<Float>, labels: List<String>, modifier: Modifier) {
    Box(modifier) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            data.forEachIndexed { index, value ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        Modifier
                            .width(30.dp)
                            .fillMaxHeight(value)
                            .background(Primary.copy(alpha = 0.7f), RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                    )
                    Text(labels[index], fontSize = 10.sp, color = Color.Gray)
                }
            }
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
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}
