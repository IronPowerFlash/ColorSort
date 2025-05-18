package com.flexi.colorsort.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
                        String.format(stringResource(R.string.solved_Header), gameViewModel.moves),
                        fontSize = 48.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 48.sp
                    )
                    Button(onClick = {gameViewModel.newGame()}) {
                        Text(stringResource(R.string.btn_Restart))
                    }
                }
            }
        } else {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconActionButton(
                    icon = Icons.Filled.Refresh,
                    textResourceId = R.string.restart,
                    onClick = { gameViewModel.restart()})

                Button(onClick = { gameViewModel.newGame()}) {
                    Text(stringResource(R.string.new_game))
                }
                IconActionButton(
                    image = painterResource(R.drawable.undo_24px),
                    textResourceId = R.string.undo,
                    onClick = { gameViewModel.undo() },
                    enabled = gameViewModel.canUndo)
            }
            BrickContainerList(
                containers = gameViewModel.containers,
                maxBricks = gameViewModel.settings.containerSize,
                onContainerSelected = { container -> gameViewModel.setContainerSelected(container) },
                modifier = modifier
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally) { // .fillMaxWidth(0.3F)
                    Text(
                        "?",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                    Text(
                        stringResource(R.string.level), textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                }
                IconActionButton(
                    icon = Icons.Outlined.Settings,
                    textResourceId = R.string.btn_Settings,
                    onClick = {settingsDialogOpen.value = true})

                Column(modifier = Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally) { //.fillMaxWidth(0.3F)
                    Text(
                        gameViewModel.moves.toString(), textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                    Text(
                        stringResource(R.string.moves), textAlign = TextAlign.Center,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

@Composable
fun IconActionButton (
    icon: ImageVector? = null,
    image: Painter? = null,
    textResourceId:Int,
    onClick: ()->Unit,
    enabled:Boolean = true
) {
    Button(onClick = onClick,
        enabled = enabled,
        colors = ButtonColors(Color.Transparent, Color.White,  Color.Transparent, Color.Gray),
    ) {
        val color = if (enabled) Color.White else Color.Gray
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ){
            if(icon != null) {
                Icon(
                    icon,
                    tint = color,
                    contentDescription = stringResource(textResourceId),
                )
            }
            else if(image != null){
                Icon(
                    image,
                    tint = color,
                    contentDescription = stringResource(textResourceId),
                )
            }
            Text(stringResource(textResourceId), color = color)
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun GameScreenPreview(){
    var viewModel = GameViewModel()
    GameScreen(modifier = Modifier, viewModel)
}

