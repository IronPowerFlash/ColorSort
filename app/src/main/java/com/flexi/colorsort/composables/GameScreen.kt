package com.flexi.colorsort.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flexi.colorsort.R
import com.flexi.colorsort.models.GameViewModel


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
) {
    val settingsDialogOpen = remember {mutableStateOf(false)}
    when {
        settingsDialogOpen.value ->{
            SettingsDialog(
                onDismissRequest = {settingsDialogOpen.value = false},
                onConfirmation = {newSettings ->
                    gameViewModel.updateSettings(newSettings)
                    settingsDialogOpen.value = false},
                settings = gameViewModel.settings
            )
        }

    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (gameViewModel.isSolved) {
            Row(
                modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = modifier.padding(start = 20.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        stringResource(R.string.solved_Header),
                        fontSize = 48.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 48.sp
                    )
                    Button(onClick = {gameViewModel.restartGame()}) {
                        Text(stringResource(R.string.btn_Restart))
                    }
                }
            }
        } else {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {settingsDialogOpen.value = true}) {
                    Text(stringResource(R.string.btn_Settings))
                }
//                Button(onClick = {}) {
//                    Text("Undo")
//                }
            }
            BrickContainerList(
                containers = gameViewModel.containers,
                maxBricks = gameViewModel.settings.containerSize,
                onContainerSelected = { container -> gameViewModel.setContainerSelected(container) },
                modifier = modifier
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = modifier.fillMaxWidth(0.3F)) {
                    Text(
                        "?",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = modifier.fillMaxWidth()
                    )
                    Text(
                        stringResource(R.string.level), textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = modifier.fillMaxWidth()
                    )
                }
                Column(modifier = modifier.fillMaxWidth(0.3F)) {
                    Text(
                        gameViewModel.moves.toString(), textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = modifier.fillMaxWidth()
                    )
                    Text(
                        stringResource(R.string.moves), textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

