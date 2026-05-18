package com.example.deolhonolixo.ui.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.deolhonolixo.ui.theme.Primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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

fun calcularProximaColeta(bairro: BairroInfo): Pair<Long, String> {
    val agora = Calendar.getInstance()
    val proxima = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, bairro.horaInicio)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    
    if (!proxima.after(agora)) {
        proxima.add(Calendar.DAY_OF_YEAR, 1)
    }
    
    while (!bairro.diasSemana.contains(proxima.get(Calendar.DAY_OF_WEEK))) {
        proxima.add(Calendar.DAY_OF_YEAR, 1)
    }
    
    val segundos = (proxima.timeInMillis - agora.timeInMillis) / 1000
    val formatada = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(proxima.time)
    return Pair(segundos, formatada)
}

fun gerarUltimasColetas(bairro: BairroInfo, quantidade: Int = 4): List<UltimaColeta> {
    val ultimas = mutableListOf<UltimaColeta>()
    val cal = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    
    var count = 0
    while (count < quantidade) {
        cal.add(Calendar.DAY_OF_YEAR, -1)
        if (bairro.diasSemana.contains(cal.get(Calendar.DAY_OF_WEEK))) {
            ultimas.add(UltimaColeta(sdf.format(cal.time)))
            count++
        }
    }
    return ultimas
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(onAdminClick: () -> Unit = {}) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    var selectedBairro by remember { mutableStateOf(listaBairrosGeo[7]) }
    var secondsRemaining by remember { mutableLongStateOf(0L) }
    var nextDate by remember { mutableStateOf("") }

    LaunchedEffect(selectedBairro) {
        val (segundos, data) = calcularProximaColeta(selectedBairro)
        secondsRemaining = segundos
        nextDate = data
        while (secondsRemaining > 0) {
            delay(1000)
            secondsRemaining--
        }
    }

    val ultimasColetas = remember(selectedBairro) {
        gerarUltimasColetas(selectedBairro)
    }

    val timeDisplay = remember(secondsRemaining) {
        val h = secondsRemaining / 3600
        val m = (secondsRemaining % 3600) / 60
        val s = secondsRemaining % 60
        String.format(Locale.US, "%02d:%02d:%02d", h, m, s)
    }

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
                    AndroidView(
                        factory = { context ->
                            WebView(context).apply {
                                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                                webViewClient = WebViewClient()
                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                loadUrl("http://10.0.2.2:5174/mapa?bairro=${selectedBairro.id}")
                            }
                        },
                        update = { it.loadUrl("http://10.0.2.2:5174/mapa?bairro=${selectedBairro.id}") },
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
                            ExposedDropdownMenuBox(expanded, { expanded = !expanded }) {
                                OutlinedTextField(
                                    value = selectedBairro.displayNome,
                                    onValueChange = {},
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                                    shape = RoundedCornerShape(12.dp)
                                )
                                ExposedDropdownMenu(expanded, { expanded = false }) {
                                    listaBairrosGeo.forEach { b ->
                                        DropdownMenuItem(
                                            text = { Text(b.displayNome) },
                                            onClick = { selectedBairro = b; expanded = false }
                                        )
                                    }
                                }
                            }
                            Text("A coleta chegará no bairro em:", Modifier.padding(top = 24.dp), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(timeDisplay, fontSize = 64.sp, fontWeight = FontWeight.Medium, color = Primary)
                            
                            Card(
                                Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = Primary),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(Modifier.padding(20.dp)) {
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                        Text("Próxima Coleta:", color = Color.White, fontWeight = FontWeight.Bold)
                                        Text(nextDate, color = Color.White)
                                    }
                                    Spacer(Modifier.height(12.dp))
                                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                                        Text("Horário Previsto:", color = Color.White, fontWeight = FontWeight.Bold)
                                        Text("${selectedBairro.horarioPrevisto} (${selectedBairro.periodo})", color = Color.White)
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
                                    ultimasColetas.forEach { coleta ->
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
