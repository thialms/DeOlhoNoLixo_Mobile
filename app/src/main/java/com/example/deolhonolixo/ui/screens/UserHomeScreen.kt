package com.example.deolhonolixo.ui.screens

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
    val diasSemana: List<Int>, // 1 = Domingo, 2 = Segunda, ..., 7 = Sábado
    val horaExecucao: Int // Hora em formato 24h
)

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

data class ColetaStatus(
    val segundosRestantes: Long,
    val dataTexto: String
)

fun calcularStatusColeta(bairro: BairroInfo): ColetaStatus {
    val agora = Calendar.getInstance()
    var proxima = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, bairro.horaExecucao)
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
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
    
    return ColetaStatus(segundos, sdf.format(proxima.time))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(
    onAdminClick: () -> Unit = {}
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var expanded by remember { mutableStateOf(false) }
    var selectedBairro by remember { mutableStateOf(listaBairrosGeo[7]) }

    var secondsRemaining by remember { mutableLongStateOf(0L) }
    var nextCollectionDate by remember { mutableStateOf("") }

    LaunchedEffect(selectedBairro) {
        val status = calcularStatusColeta(selectedBairro)
        secondsRemaining = status.segundosRestantes
        nextCollectionDate = status.dataTexto
        while (secondsRemaining > 0) {
            delay(1000)
            secondsRemaining--
        }
    }

    val timeDisplay = remember(secondsRemaining) {
        val hours = secondsRemaining / 3600
        val minutes = (secondsRemaining % 3600) / 60
        val seconds = secondsRemaining % 60
        String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Menu Principal",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
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
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.databaseEnabled = true
                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        loadUrl("http://10.0.2.2:5174/mapa?bairro=${selectedBairro.id}")
                    }
                },
                update = { webView ->
                    webView.loadUrl("http://10.0.2.2:5174/mapa?bairro=${selectedBairro.id}")
                },
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
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                shadowElevation = 16.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = selectedBairro.displayNome,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = Primary
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listaBairrosGeo.forEach { bairro ->
                                DropdownMenuItem(
                                    text = { Text(bairro.displayNome) },
                                    onClick = {
                                        selectedBairro = bairro
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "A coleta chegará no bairro em:",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = timeDisplay,
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Medium,
                        color = Primary,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Primary),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Próxima Coleta:", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text(nextCollectionDate, color = Color.White, fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Horário Previsto:", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                Text("${selectedBairro.horarioPrevisto} (${selectedBairro.periodo})", color = Color.White, fontSize = 18.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Perdeu o horário da coleta?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Text(
                        text = "Crie um alerta para lembrar",
                        fontSize = 16.sp,
                        color = Primary,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clickable { },
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
