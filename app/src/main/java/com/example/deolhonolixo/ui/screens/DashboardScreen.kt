package com.example.deolhonolixo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deolhonolixo.data.api.Truck
import com.example.deolhonolixo.data.api.UrbanGeometryResponse
import com.example.deolhonolixo.data.api.RouteResponse
import com.example.deolhonolixo.data.api.TruckHistory
import com.example.deolhonolixo.ui.theme.Primary

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = viewModel()) {
    var currentTab by remember { mutableIntStateOf(0) }
    val trucks by viewModel.trucks.collectAsState()
    val routes by viewModel.routes.collectAsState()
    val urbanGeometry by viewModel.urbanGeometry.collectAsState()
    val history by viewModel.truckHistory.collectAsState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentTab) { currentTab = it }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentTab) {
                0 -> MonitoramentoTab(trucks, urbanGeometry, routes)
                1 -> AlertasTab(history)
                2 -> RelatoriosTab(trucks, routes)
                3 -> AjustesTab(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoramentoTab(
    trucks: List<Truck>, 
    urbanGeometry: List<UrbanGeometryResponse>,
    routes: List<RouteResponse>
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedBairro by remember { mutableStateOf("Todos os Setores") }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF3F4F6))) {
            trucks.forEachIndexed { index, truck ->
                Icon(
                    Icons.Default.LocalShipping, 
                    null, 
                    Modifier.offset(x = (40 + index * 30).dp, y = (100 + index * 50).dp).size(28.dp), 
                    tint = if (truck.status == "Ativo") Color(0xFF84CC16) else Primary
                )
            }
        }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.padding(16.dp).align(Alignment.TopStart)
        ) {
            OutlinedTextField(
                value = selectedBairro,
                onValueChange = {},
                readOnly = true,
                label = { Text("Filtro Geográfico") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().background(Color.White, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Todos os Setores") },
                    onClick = { selectedBairro = "Todos os Setores"; expanded = false }
                )
                urbanGeometry.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.name) },
                        onClick = {
                            selectedBairro = item.name
                            expanded = false
                        }
                    )
                }
            }
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().fillMaxHeight(0.5f),
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
                    Text("Painel Admin - Frota PG", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp)
                }
                item { MetricCard("Frota Total", "${trucks.size}", Color(0xFF7B8AD7), Color.White) }
                item { MetricCard("Rotas Ativas", "${routes.size}", Color.White, Color(0xFFEAB308), border = Color(0xFFE5E7EB)) }
                
                item(span = { GridItemSpan(2) }) {
                    HorizontalMetricCard("Status Operacional", "Normal", "Sincronizado com API Java", Color(0xFF84CC16))
                }
            }
        }
    }
}

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

@Composable
fun RelatoriosTab(trucks: List<Truck>, routes: List<RouteResponse>) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState())
    ) {
        Text("Análise de Dados", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))

        ReportCard("Eficiência da Frota", "88%", "Baseado em ${trucks.size} caminhões", Primary)
        Spacer(modifier = Modifier.height(16.dp))
        ReportCard("Cobertura Urbana", "${routes.size} Rotas", "Total de bairros mapeados", Color(0xFF84CC16))

        Spacer(modifier = Modifier.height(24.dp))
        Text("Lista de Caminhões Registrados", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        trucks.forEach { truck ->
            Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(truck.licensePlate, color = Color.DarkGray)
                Text(truck.model, fontWeight = FontWeight.Bold, color = Primary)
            }
            HorizontalDivider(color = Color(0xFFF3F4F6))
        }
    }
}

@Composable
fun AjustesTab(viewModel: DashboardViewModel) {
    var plate by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp).verticalScroll(rememberScrollState())) {
        Text("Gerenciamento do Sistema", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Registrar Novo Caminhão", fontWeight = FontWeight.Bold, color = Primary)
        Card(
            Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
        ) {
            Column(Modifier.padding(16.dp)) {
                OutlinedTextField(value = plate, onValueChange = { plate = it }, label = { Text("Placa") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Modelo") }, modifier = Modifier.fillMaxWidth())
                Button(
                    onClick = { viewModel.registerTruck(plate, model); plate = ""; model = "" },
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Registrar via POST /trucks")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        SettingSection(title = "Configurações de Rede") {
            SettingItem(icon = Icons.Default.Dns, title = "Endpoint API", value = "http://10.0.2.2:8080")
            SettingItem(icon = Icons.Default.CloudSync, title = "Sync automático", value = "Ativado")
        }
    }
}

@Composable
fun ReportCard(title: String, value: String, description: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, color = Color.Gray)
                Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = color)
                Text(description, fontSize = 12.sp, color = Color.Gray.copy(alpha = 0.8f))
            }
            Icon(Icons.AutoMirrored.Filled.TrendingUp, null, tint = color, modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun SettingSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Primary, modifier = Modifier.padding(bottom = 8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
        ) {
            Column(content = content)
        }
    }
}

@Composable
fun SettingItem(icon: ImageVector, title: String, value: String? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { }.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, modifier = Modifier.weight(1f), fontSize = 16.sp)
        if (value != null) Text(value, color = Color.Gray, fontSize = 14.sp)
        else Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
    }
}

@Composable
fun MetricCard(title: String, value: String, bgColor: Color, contentColor: Color, sub: String? = null, border: Color? = null, hasProgress: Boolean = false, isCircular: Boolean = false) {
    Card(modifier = Modifier.height(140.dp), colors = CardDefaults.cardColors(containerColor = bgColor), shape = RoundedCornerShape(20.dp), border = border?.let { androidx.compose.foundation.BorderStroke(1.dp, it) }) {
        Column(Modifier.fillMaxSize().padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)) {
                if (hasProgress || isCircular) CircularProgressIndicator(progress = { 0.7f }, color = contentColor.copy(0.6f), trackColor = Color.White.copy(0.2f))
                Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = contentColor)
            }
            Text(title, fontSize = 14.sp, color = contentColor, fontWeight = FontWeight.Medium)
            if (sub != null) Text(sub, fontSize = 10.sp, color = if (bgColor == Color.White) Color.Gray else Color.White.copy(0.7f))
        }
    }
}

@Composable
fun HorizontalMetricCard(title: String, value: String, desc: String, color: Color) {
    Card(Modifier.fillMaxWidth().height(90.dp), colors = CardDefaults.cardColors(containerColor = color.copy(0.1f)), shape = RoundedCornerShape(16.dp)) {
        Row(Modifier.fillMaxSize().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                Text(desc, fontSize = 10.sp, color = Color.Gray)
            }
            Text(value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = color)
        }
    }
}

@Composable
fun BottomNavigationBar(selected: Int, onSelect: (Int) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        val menu = listOf("Monitor" to Icons.Default.Map, "Alertas" to Icons.Default.Notifications, "Gráficos" to Icons.Default.BarChart, "Sistema" to Icons.Default.Settings)
        menu.forEachIndexed { i, (label, icon) ->
            NavigationBarItem(
                selected = selected == i, 
                onClick = { onSelect(i) }, 
                icon = { Icon(icon, null) }, 
                label = { Text(label) }, 
                colors = NavigationBarItemDefaults.colors(selectedIconColor = Primary, selectedTextColor = Primary)
            )
        }
    }
}

@Composable fun AlertItem(msg: String, color: Color) {
    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = color.copy(0.1f))) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, null, tint = color)
            Spacer(Modifier.width(12.dp))
            Text(msg, fontSize = 14.sp)
        }
    }
}
