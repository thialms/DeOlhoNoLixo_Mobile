package com.example.deolhonolixo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deolhonolixo.ui.screens.dashboard.*
import com.example.deolhonolixo.ui.theme.Primary

@Composable
fun DashboardScreen(
    onLogout: () -> Unit = {},
    viewModel: DashboardViewModel = viewModel()
) {
    var currentTab by remember { mutableIntStateOf(0) }
    val trucks by viewModel.trucks.collectAsState()
    val routes by viewModel.routes.collectAsState()
    val urbanGeometry by viewModel.urbanGeometry.collectAsState()
    val history by viewModel.truckHistory.collectAsState()

    Scaffold(
        bottomBar = {
            DashboardBottomNavigation(currentTab) { currentTab = it }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentTab) {
                0 -> MonitoramentoTab(trucks, urbanGeometry, routes)
                1 -> AlertasTab(history)
                2 -> RelatoriosTab(trucks, routes)
                3 -> AjustesTab(viewModel, onLogout)
            }
        }
    }
}

@Composable
fun DashboardBottomNavigation(selected: Int, onSelect: (Int) -> Unit) {
    NavigationBar(containerColor = Color.White) {
        val items = listOf(
            "Monitor" to Icons.Default.Map,
            "Alertas" to Icons.Default.Notifications,
            "Gráficos" to Icons.Default.BarChart,
            "Sistema" to Icons.Default.Settings
        )
        items.forEachIndexed { index, (label, icon) ->
            NavigationBarItem(
                selected = selected == index,
                onClick = { onSelect(index) },
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary
                )
            )
        }
    }
}
