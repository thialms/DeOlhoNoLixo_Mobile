package com.example.deolhonolixo.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deolhonolixo.data.model.RouteResponse
import com.example.deolhonolixo.data.model.Truck
import com.example.deolhonolixo.data.model.UrbanGeometryResponse
import com.example.deolhonolixo.data.model.listaBairrosGeo
import com.example.deolhonolixo.ui.components.BairroDropdown
import com.example.deolhonolixo.ui.components.WasteMapView

@Composable
fun MonitoramentoTab(
    trucks: List<Truck>,
    urbanGeometry: List<UrbanGeometryResponse>,
    routes: List<RouteResponse>
) {
    // Reutilizando a lógica de estado do usuário para o mapa
    var selectedBairro by remember { mutableStateOf(listaBairrosGeo[0]) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Reutilizando exatamente o mesmo mapa
        WasteMapView(
            bairroId = selectedBairro.id,
            modifier = Modifier.fillMaxSize()
        )

        // Reutilizando exatamente o mesmo dropdown
        BairroDropdown(
            selectedBairro = selectedBairro,
            onBairroSelected = { selectedBairro = it },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopStart)
                .width(250.dp)
                .background(Color.White.copy(alpha = 0.9f), RoundedCornerShape(12.dp))
        )

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.4f),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            shadowElevation = 16.dp
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item(span = { GridItemSpan(2) }) {
                    Text(
                        text = "Painel Admin - Frota PG",
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
                item {
                    MetricCard("Frota Total", "${trucks.size}", Color(0xFF7B8AD7), Color.White)
                }
                item {
                    MetricCard(
                        title = "Rotas Ativas",
                        value = "${routes.size}",
                        bgColor = Color.White,
                        contentColor = Color(0xFFEAB308),
                        border = Color(0xFFE5E7EB)
                    )
                }

                item(span = { GridItemSpan(2) }) {
                    HorizontalMetricCard(
                        title = "Status Operacional",
                        value = "Normal",
                        desc = "Sincronizado com API Java",
                        color = Color(0xFF84CC16)
                    )
                }
            }
        }
    }
}

@Composable
fun HorizontalMetricCard(title: String, value: String, desc: String, color: Color) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(90.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                Text(desc, fontSize = 10.sp, color = Color.Gray)
            }
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = color)
        }
    }
}
