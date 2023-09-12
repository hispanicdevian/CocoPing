package com.example.cocoping

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun CocoPingScreen(viewModel: CocoPingViewModel) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait) {
        CocoPingPortrait(viewModel)
    } else {
        CocoPingLandscape(viewModel)
    }
}