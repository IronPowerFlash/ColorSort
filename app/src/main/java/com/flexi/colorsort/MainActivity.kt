package com.flexi.colorsort

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.flexi.colorsort.composables.GameScreen
import com.flexi.colorsort.models.GameViewModel
import com.flexi.colorsort.ui.theme.ColorSortTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val gameViewModel: GameViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ColorSortTheme {

                Surface(modifier = Modifier.fillMaxSize()
                    .statusBarsPadding().navigationBarsPadding(),
                    color = MaterialTheme.colorScheme.background) {
                    GameScreen(gameViewModel = gameViewModel, modifier = Modifier)
                }
            }
        }
    }
}

