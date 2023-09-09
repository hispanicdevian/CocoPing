package com.example.cocoping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocoPingScreen(viewModel: CocoPingViewModel) {
    val count = 5
    val packetSize = 64

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Coco Ping", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = viewModel.ipOrHostname,
                onValueChange = { viewModel.ipOrHostname = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textStyle = LocalTextStyle.current,
                singleLine = true,
                placeholder = { Text("Insert IP or Hostname") }
            )

            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.performPing(count, packetSize)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text("Submit", fontWeight = FontWeight.SemiBold)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.LightGray)
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(state = scrollState)
            ) {
                Text("Coco Request", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(8.dp))
                LaunchedEffect(viewModel.pingResults) {
                    scrollState.animateScrollTo(scrollState.maxValue)
                }
                viewModel.pingResults.forEachIndexed { index, result ->
                    Text(result)
                    if (index < viewModel.pingResults.size - 1) {
                        Divider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 2.dp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
