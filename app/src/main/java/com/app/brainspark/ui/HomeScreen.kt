package com.app.brainspark.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            // هنا يتم وضع إعلان البانر (Banner Ad) في التطبيق الحقيقي
            Surface(color = MaterialTheme.colorScheme.surfaceVariant, modifier = Modifier.fillMaxWidth().height(50.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Text("AdMob Banner Ad", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("سجل التتابع: ${uiState.streak} 🔥", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("العملات: ${uiState.coins} 💰", style = MaterialTheme.typography.titleLarge)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(8.dp)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("لغز اليوم:", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(uiState.currentRiddle, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { viewModel.solveRiddle() }, 
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isAdLoading
            ) {
                Text("حل اللغز وكسب 10 عملات")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(
                onClick = { viewModel.showInterstitialAd() }, 
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isAdLoading
            ) {
                if (uiState.isAdLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("شاهد إعلاناً مقابل 50 عملة")
                }
            }
        }
    }
}
