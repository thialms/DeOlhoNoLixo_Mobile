package com.example.deolhonolixo.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deolhonolixo.data.model.TruckHistory
import com.example.deolhonolixo.ui.theme.Primary

@Composable
fun AlertasTab(history: List<TruckHistory>) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Histórico e Alertas", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))

        if (history.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhum histórico recente detectado.", color = Color.Gray)
            }
        } else {
            LazyColumn {
                items(history) { entry ->
                    AlertItem("Caminhão ${entry.truckId} em Lat: ${entry.location.latitude}", Primary)
                }
            }
        }
    }
}
