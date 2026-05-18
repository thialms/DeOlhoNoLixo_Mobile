package com.example.deolhonolixo.ui.screens.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deolhonolixo.ui.theme.Primary

@Composable
fun MetricCard(
    title: String,
    value: String,
    bgColor: Color,
    contentColor: Color,
    sub: String? = null,
    border: Color? = null,
    hasProgress: Boolean = false
) {
    Card(
        modifier = Modifier.height(140.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(20.dp),
        border = border?.let { androidx.compose.foundation.BorderStroke(1.dp, it) }
    ) {
        Column(
            Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.size(50.dp)) {
                if (hasProgress) {
                    CircularProgressIndicator(
                        progress = { 0.7f },
                        color = contentColor.copy(0.6f),
                        trackColor = Color.White.copy(0.2f)
                    )
                }
                Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = contentColor)
            }
            Text(title, fontSize = 14.sp, color = contentColor, fontWeight = FontWeight.Medium)
            if (sub != null) {
                Text(
                    text = sub,
                    fontSize = 10.sp,
                    color = if (bgColor == Color.White) Color.Gray else Color.White.copy(0.7f)
                )
            }
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
fun SettingItem(icon: ImageVector, title: String, value: String? = null, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(16.dp),
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
fun AlertItem(msg: String, color: Color) {
    Card(
        Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Warning, null, tint = color)
            Spacer(Modifier.width(12.dp))
            Text(msg, fontSize = 14.sp)
        }
    }
}
