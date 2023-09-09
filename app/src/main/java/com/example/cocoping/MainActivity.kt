package com.example.cocoping

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.ViewModelProvider
import com.example.cocoping.ui.theme.CocoPingTheme

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this)[CocoPingViewModel::class.java]

        setContent {
            CocoPingTheme {
                CocoPingScreen(viewModel)
            }
        }
    }
}