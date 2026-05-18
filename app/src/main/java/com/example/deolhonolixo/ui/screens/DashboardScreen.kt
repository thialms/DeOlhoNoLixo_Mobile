package com.example.deolhonolixo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deolhonolixo.ui.theme.Primary

@Composable
fun DashboardScreen() {
    var currentTab by remember { mutableIntStateOf(0) }
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(currentTab) { currentTab = it }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentTab) {
                0 -> MonitoramentoTab()
                1 -> AlertasTab()
                2 -> RelatoriosTab()
                3 -> AjustesTab()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonitoramentoTab() {
    val bairros = listOf(
        "Militar", "Canto do Forte", "Boqueirão", "Guilhermina", "Aviação", "Tupi", 
        "Ocian", "Mirim", "Maracanã", "Caiçara", "Real", "Flórida", "Solemar", 
        "Cidade da Criança", "Princesa", "Imperador", "Melvi", "Samambaia", 
        "Esmeralda", "Ribeirópolis", "Andaraguá", "Nova Mirim", "Anhanguera", 
        "Quietude", "Santa Marina", "Tupiry", "Antártica", "Vila Sônia", 
        "Glória", "Sítio do Campo", "Xixová", "Serra do Mar"
    )
    
    var expanded by remember { mutableStateOf(false) }
    var selectedBairro by remember { mutableStateOf(bairros[2]) }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFFF3F4F6))) {
            Icon(Icons.Default.LocalShipping, null, Modifier.offset(120.dp, 150.dp).size(28.dp), tint = Primary)
            Icon(Icons.Default.LocalShipping, null, Modifier.offset(200.dp, 300.dp).size(28.dp), tint = Color(0xFF84CC16))
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
                label = { Text("Setor (Bairro)") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor().background(Color.White, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                bairros.forEach { bairro ->
                    DropdownMenuItem(
                        text = { Text(bairro) },
                        onClick = {
                            selectedBairro = bairro
                            expanded = false
                        }
                    )
                }
            }
        }

        Surface(
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().fillMaxHeight(0.65f),
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
                    Text("Monitoramento em Tempo Real", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 12.sp)
                }
                item { MetricCard("Coletas", "13", Color(0xFF7B8AD7), Color.White, hasProgress = true) }
                item { MetricCard("Caminhões", "08", Color.White, Color(0xFFEAB308), "Ativos Hoje", Color(0xFFE5E7EB)) }
                
                item(span = { GridItemSpan(2) }) {
                    HorizontalMetricCard("Capacidade Caçamba", "78%", "Sensor ESP32-Lixo", Color(0xFF84CC16))
                }
                
                item { MetricCard("Combustível", "62%", Color.White, Color(0xFFEF4444), "Média Frota", Color(0xFFE5E7EB), isCircular = true) }
                item { MetricCard("Distância", "312km", Color(0xFFF472B6), Color.White) }
            }
        }
    }
}

@Composable
fun RelatoriosTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Relatórios e Métricas", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))

        ReportCard(
            title = "Eficiência da Coleta",
            value = "94%",
            description = "Aumento de 5% em relação à semana passada",
            color = Primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReportCard(
            title = "Volume de Resíduos",
            value = "142.5 Ton",
            description = "Total coletado em Praia Grande hoje",
            color = Color(0xFF84CC16)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ReportCard(
            title = "Economia de Combustível",
            value = "R$ 1.240,00",
            description = "Otimização de rotas via IA",
            color = Color(0xFFEAB308)
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        Text("Top Bairros (Volume)", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        
        val topBairros = listOf("Boqueirão" to "12.4t", "Canto do Forte" to "10.8t", "Guilhermina" to "9.5t")
        topBairros.forEach { (bairro, volume) ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(bairro, color = Color.Gray)
                Text(volume, fontWeight = FontWeight.Bold, color = Primary)
            }
            HorizontalDivider(color = Color(0xFFF3F4F6), thickness = 1.dp)
        }
        
        Spacer(modifier = Modifier.height(48.dp))
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
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
fun AjustesTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Configurações", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))

        SettingSection(title = "Conexão IoT") {
            SettingItem(icon = Icons.Default.Dns, title = "Broker MQTT", value = "192.168.1.100")
            SettingItem(icon = Icons.Default.VpnKey, title = "Chave da API Java", value = "••••••••••••")
            SettingItem(icon = Icons.Default.DeviceHub, title = "Dispositivos ESP32", value = "14 ativos")
        }

        Spacer(modifier = Modifier.height(24.dp))

        SettingSection(title = "Notificações de Campo") {
            SettingToggle(title = "Alertas de Caçamba Cheia", initial = true)
            SettingToggle(title = "Desvios de Rota", initial = true)
            SettingToggle(title = "Relatórios Diários", initial = false)
        }

        Spacer(modifier = Modifier.height(24.dp))

        SettingSection(title = "Sistema") {
            SettingItem(icon = Icons.Default.History, title = "Limpar Cache de Mapas")
            SettingItem(icon = Icons.Default.Info, title = "Versão do App", value = "1.0.4-alpha")
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
            ) {
                Text("Sair da Conta Administrativa", fontWeight = FontWeight.Bold)
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
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
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, modifier = Modifier.weight(1f), fontSize = 16.sp)
        if (value != null) {
            Text(value, color = Color.Gray, fontSize = 14.sp)
        } else {
            Icon(Icons.Default.ChevronRight, null, tint = Color.LightGray)
        }
    }
}

@Composable
fun SettingToggle(title: String, initial: Boolean) {
    var checked by remember { mutableStateOf(initial) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontSize = 16.sp)
        Switch(checked = checked, onCheckedChange = { checked = it }, colors = SwitchDefaults.colors(checkedThumbColor = Primary))
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

@Composable fun AlertasTab() { 
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text("Alertas do Sistema", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))
        AlertItem("Caminhão #04: Nível de óleo crítico", Color.Red)
        AlertItem("Sítio do Campo: Lixeira 90%", Color(0xFFFFC107))
        AlertItem("Caminhão #02: Fora de rota", Color(0xFFFF9800))
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
