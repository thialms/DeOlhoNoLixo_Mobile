package com.example.deolhonolixo.ui.screens

import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    val diasSemana: List<Int>,
    val horaInicio: Int
)

val listaBairrosGeo = listOf(
    BairroInfo("militar", "Militar", "DIA", "07:00", listOf(1,2,3,4,5,6,7), 7),
    BairroInfo("canto-do-forte", "Canto do Forte", "NOITE", "18:00", listOf(2,4,6), 18),
    BairroInfo("boqueirao", "Boqueirão", "NOITE", "18:00", listOf(2,4,6), 18),
    BairroInfo("guilhermina", "Guilhermina", "NOITE", "18:00", listOf(2,4,6), 18),
    BairroInfo("aviacao", "Aviação", "NOITE", "18:00", listOf(2,4,6), 18),
    BairroInfo("tupi", "Tupi", "NOITE", "18:00", listOf(2,4,6), 18),
    BairroInfo("ocian", "Ocian", "NOITE", "18:00", listOf(3,5,1), 18),
    BairroInfo("mirim", "Mirim", "NOITE", "18:00", listOf(3,5,1), 18),
    BairroInfo("maracana", "Maracanã", "NOITE", "18:00", listOf(3,5,1), 18),
    BairroInfo("caicara", "Caiçara", "NOITE", "18:00", listOf(3,5,1), 18),
    BairroInfo("real", "Real", "NOITE", "18:00", listOf(3,5,1), 18),
    BairroInfo("florida", "Flórida", "DIA", "06:00", listOf(2,4,6), 6),
    BairroInfo("solemar", "Solemar", "DIA", "06:00", listOf(2,4,6), 6),
    BairroInfo("cidade-da-crianca", "Cidade da Criança", "DIA", "06:00", listOf(2,4,6), 6),
    BairroInfo("princesa", "Princesa", "DIA", "06:00", listOf(2,4,6), 6),
    BairroInfo("imperador", "Imperador", "DIA", "06:00", listOf(2,4,6), 6),
    BairroInfo("melvi", "Melvi", "DIA", "06:00", listOf(2,4,6), 6),
    BairroInfo("samambaia", "Samambaia", "DIA", "06:00", listOf(2,4,6), 6),
    BairroInfo("esmeralda", "Esmeralda", "DIA", "06:00", listOf(2,4,6), 6),
    BairroInfo("ribeiropolis", "Ribeirópolis", "DIA", "06:00", listOf(2,4,6), 6),
    BairroInfo("andaragua", "Andaraguá", "DIA", "07:00", listOf(1,2,3,4,5,6,7), 7),
    BairroInfo("nova-mirim", "Nova Mirim", "DIA", "06:00", listOf(3,5,7), 6),
    BairroInfo("anhanguera", "Anhanguera", "DIA", "06:00", listOf(3,5,7), 6),
    BairroInfo("quietude", "Quietude", "DIA", "06:00", listOf(3,5,7), 6),
    BairroInfo("santa-marina", "Santa Marina", "DIA", "06:00", listOf(3,5,7), 6),
    BairroInfo("tupiry", "Tupiry", "DIA", "06:00", listOf(3,5,7), 6),
    BairroInfo("antartica", "Antártica", "DIA", "06:00", listOf(3,5,7), 6),
    BairroInfo("vila-sonia", "Vila Sônia", "DIA", "06:00", listOf(3,5,7), 6),
    BairroInfo("gloria", "Glória", "DIA", "06:00", listOf(3,5,7), 6),
    BairroInfo("sitio-do-campo", "Sítio do Campo", "NOITE", "18:00", listOf(2,4,6), 18),
    BairroInfo("xixova", "Xixová", "DIA", "07:00", listOf(1,2,3,4,5,6,7), 7),
    BairroInfo("serra-do-mar", "Serra do Mar", "DIA", "07:00", listOf(1,2,3,4,5,6,7), 7)
)

data class ColetaStatus(val segundos: Long, val data: String)

fun calcularProximaColeta(bairro: BairroInfo): ColetaStatus {
    val agora = Calendar.getInstance()
    val proxima = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, bairro.horaInicio)
        set(Calendar.MINUTE, 0); set(Calendar.SECOND, 0); set(Calendar.MILLISECOND, 0)
    }
    if (!proxima.after(agora)) proxima.add(Calendar.DAY_OF_YEAR, 1)
    while (!bairro.diasSemana.contains(proxima.get(Calendar.DAY_OF_WEEK))) proxima.add(Calendar.DAY_OF_YEAR, 1)
    
    val segundos = (proxima.timeInMillis - agora.timeInMillis) / 1000
    val dataFormatada = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR")).format(proxima.time)
    return ColetaStatus(segundos, dataFormatada)
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
        val status = calcularProximaColeta(selectedBairro)
        secondsRemaining = status.segundos
        nextDate = status.data
        while (secondsRemaining > 0) { delay(1000); secondsRemaining-- }
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
                        Row(Modifier.fillMaxWidth().padding(16.dp), Arrangement.SpaceBetween, Alignment.CenterVertically) {
                            Text("Menu Principal", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            IconButton(onClick = { scope.launch { drawerState.close() } }) { Icon(Icons.Default.Close, null) }
                        }
                        HorizontalDivider()
                        NavigationDrawerItem(
                            label = { Text("Entrar como Administrador") },
                            selected = false,
                            onClick = { scope.launch { drawerState.close() }; onAdminClick() },
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
                                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                                loadUrl("http://10.0.2.2:5174/mapa?bairro=${selectedBairro.id}")
                            }
                        },
                        update = { it.loadUrl("http://10.0.2.2:5174/mapa?bairro=${selectedBairro.id}") },
                        modifier = Modifier.fillMaxSize()
                    )

                    IconButton(
                        onClick = { scope.launch { drawerState.open() } },
                        modifier = Modifier.align(Alignment.TopEnd).padding(top = 48.dp, end = 16.dp).size(56.dp).clip(RoundedCornerShape(16.dp)).background(Primary.copy(0.9f))
                    ) { Icon(Icons.Default.Menu, null, tint = Color.White) }

                    Surface(
                        Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        shadowElevation = 16.dp
                    ) {
                        Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            ExposedDropdownMenuBox(expanded, { expanded = !expanded }) {
                                OutlinedTextField(selectedBairro.displayNome, {}, Modifier.fillMaxWidth().menuAnchor(), readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }, shape = RoundedCornerShape(12.dp))
                                ExposedDropdownMenu(expanded, { expanded = false }) {
                                    listaBairrosGeo.forEach { b -> DropdownMenuItem({ Text(b.displayNome) }, { selectedBairro = b; expanded = false }) }
                                }
                            }
                            Text("A coleta chegará no bairro em:", Modifier.padding(top = 24.dp), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(timeDisplay, fontSize = 80.sp, fontWeight = FontWeight.Medium, color = Primary)
                            Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Primary), shape = RoundedCornerShape(16.dp)) {
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
                            Text("Perdeu o horário?", Modifier.padding(top = 24.dp), fontWeight = FontWeight.Bold)
                            Text("Crie um alerta para lembrar", color = Primary, modifier = Modifier.clickable { })
                        }
                    }
                }
            }
        }
    }
}
