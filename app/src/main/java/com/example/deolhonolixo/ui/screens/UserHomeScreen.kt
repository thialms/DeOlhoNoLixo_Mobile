package com.example.deolhonolixo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deolhonolixo.ui.components.BairroDropdown
import com.example.deolhonolixo.ui.components.WasteMapView
import com.example.deolhonolixo.ui.theme.Primary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(
    onAdminClick: () -> Unit = {},
    viewModel: UserHomeViewModel = viewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = false,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    ModalDrawerSheet {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            Arrangement.SpaceBetween,
                            Alignment.CenterVertically
                        ) {
                            Text("Menu Principal", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            IconButton(onClick = { scope.launch { drawerState.close() } }) {
                                Icon(Icons.Default.Close, null)
                            }
                        }
                        HorizontalDivider()
                        NavigationDrawerItem(
                            label = { Text("Entrar como Administrador") },
                            selected = false,
                            onClick = { 
                                scope.launch { drawerState.close() }
                                onAdminClick() 
                            },
                            icon = { Icon(Icons.Default.AdminPanelSettings, null) },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Box(Modifier.fillMaxSize()) {
                    WasteMapView(
                        bairroId = viewModel.selectedBairro.id,
                        modifier = Modifier.fillMaxSize()
                    )

                    IconButton(
                        onClick = { scope.launch { drawerState.open() } },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 48.dp, end = 16.dp)
                            .size(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Primary.copy(alpha = 0.9f))
                    ) { Icon(Icons.Default.Menu, null, tint = Color.White) }

                    Surface(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .fillMaxHeight(0.45f),
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        shadowElevation = 16.dp
                    ) {
                        Column(
                            Modifier
                                .padding(24.dp)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BairroDropdown(
                                selectedBairro = viewModel.selectedBairro,
                                onBairroSelected = { viewModel.updateBairro(it) },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text("A coleta chegará no bairro em:", Modifier.padding(top = 24.dp), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(viewModel.getTimeDisplay(), fontSize = 64.sp, fontWeight = FontWeight.Medium, color = Primary)
                            
                            Card(
                                Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Primary),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(Modifier.padding(20.dp)) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                        Text("Próxima Coleta:", color = Color.White, fontWeight = FontWeight.Bold)
                                        Text(viewModel.nextDate, color = Color.White)
                                    }
                                    Spacer(Modifier.height(12.dp))
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                        Text("Horário Previsto:", color = Color.White, fontWeight = FontWeight.Bold)
                                        Text("${viewModel.selectedBairro.horarioPrevisto} (${viewModel.selectedBairro.periodo})", color = Color.White)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9A825)),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(20.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Últimas coletas",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    viewModel.ultimasColetas.forEach { coleta ->
                                        Text(
                                            text = coleta.data,
                                            fontSize = 16.sp,
                                            color = Color.Black,
                                            modifier = Modifier.padding(vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(24.dp))
                            Text("Perdeu o horário?", fontWeight = FontWeight.Bold)
                            Text("Crie um alerta para lembrar", color = Primary, modifier = Modifier.clickable { })
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}
