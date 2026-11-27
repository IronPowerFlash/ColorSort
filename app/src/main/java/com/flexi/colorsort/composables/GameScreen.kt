package com.flexi.colorsort.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.core.layout.WindowSizeClass
import com.flexi.colorsort.R
import com.flexi.colorsort.models.ColorSchemes
import com.flexi.colorsort.models.GameViewModel
import com.flexi.colorsort.models.IconStore


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameViewModel: GameViewModel = viewModel(),
    windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass,
) {
    val settingsDialogOpen = remember { mutableStateOf(false) }
    when {
        settingsDialogOpen.value -> {
            SettingsScreen(
                onCancel = { settingsDialogOpen.value = false },
                onSave = { newSettings ->
                    gameViewModel.updateSettings(newSettings)
                    settingsDialogOpen.value = false
                },
                settings = gameViewModel.settings
            )
        }
        else ->
            if (gameViewModel.isSolved) {
                SolvedScreen(gameViewModel, modifier)
            } else {
                GameContent(gameViewModel,
                    windowSizeClass,
                    onSettingsClick = { settingsDialogOpen.value = true },
                    modifier)
            }
    }
}

@Composable
fun GameContent(
    gameViewModel: GameViewModel,
    windowSizeClass: WindowSizeClass,
    onSettingsClick: () ->Unit,
    modifier: Modifier = Modifier
) {
    val confirmRestartOpen = remember {mutableStateOf(false)}
    val confirmNewGameOpen = remember {mutableStateOf(false)}
    when{
        confirmRestartOpen.value -> {
            ConfirmDialog(
                text = "Restart game?",
                onCancel =  {confirmRestartOpen.value = false},
                onConfirm = {
                    gameViewModel.restart()
                    confirmRestartOpen.value = false
                } )
        }
        confirmNewGameOpen.value -> {
            ConfirmDialog(
                text = "New game?",
                onCancel =  {confirmNewGameOpen.value = false},
                onConfirm = {
                    gameViewModel.newGame()
                    confirmNewGameOpen.value = false
                } )
        }
    }
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val (topRow, containerlistRef, bottomRow) = createRefs()
        createVerticalChain(
            topRow,
            containerlistRef,
            bottomRow,
            chainStyle = ChainStyle.Packed
        )
        Row(
            modifier = modifier
                .padding(bottom=0.dp)
                .fillMaxWidth()
                .constrainAs(topRow) {
                    top.linkTo(parent.top)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconActionButton(
                icon = Icons.Filled.Refresh,
                textResourceId = R.string.btn_Restart,
                onClick = { confirmRestartOpen.value = true })

            IconActionButton(image = painterResource(R.drawable.plus_circle),
                textResourceId = R.string.new_game,
                onClick = {confirmNewGameOpen.value = true})

            IconActionButton(
                image = painterResource(R.drawable.undo_24px),
                textResourceId = R.string.undo,
                onClick = { gameViewModel.undo() },
                enabled = gameViewModel.canUndo
            )
        }
        Log.d(
            "GameScreen",
            "iconIds: ${gameViewModel.gameIconIds.joinToString(",")}"
        )
        Log.d("GameScreen", "iconResourceIds: ${gameViewModel.gameIconIds.joinToString(", "){IconStore.iconMap[it].toString()}}")
        BrickContainerList(
            containers = gameViewModel.containers,
            maxBricks = gameViewModel.settings.containerSize,
            onContainerSelected = { container ->
                gameViewModel.setContainerSelected(
                    container
                )
            },
            modifier = modifier
                .constrainAs(containerlistRef) {
                    top.linkTo(topRow.bottom)
                    bottom.linkTo(bottomRow.top)
                    height = Dimension.fillToConstraints
                },
            windowSizeClass = windowSizeClass,
            colorScheme = ColorSchemes.getColorScheme(gameViewModel.settings.colorSchemeId),
            renderIcons = gameViewModel.settings.colorBlindMode,
//            icons = gameViewModel.settings.selectedIcons,
            iconIds = gameViewModel.gameIconIds
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .constrainAs(bottomRow) {
                    top.linkTo(containerlistRef.bottom)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                image = painterResource(R.drawable.option_bar_settings),
                textResourceId = R.string.btn_Settings,
                onClick = onSettingsClick)

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
@Composable
fun SolvedScreen(
    gameViewModel: GameViewModel,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = modifier.padding(start = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    String.format(
                        stringResource(R.string.solved_Header),
                        gameViewModel.moves
                    ),
                    fontSize = 48.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 48.sp
                )
                Button(onClick = { gameViewModel.newGame() }) {
                    Text(stringResource(R.string.new_game))
                }
            }
        }
    }

}

@Composable
fun IconActionButton(
    icon: ImageVector? = null,
    image: Painter? = null,
    textResourceId: Int,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonColors(Color.Transparent, Color.White, Color.Transparent, Color.Gray),
    ) {
        val color = if (enabled) Color.White else Color.Gray
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if (icon != null) {
                Icon(
                    icon,
                    tint = color,
                    contentDescription = stringResource(textResourceId),
                    modifier = Modifier.height(24.dp)
                )
            } else if (image != null) {
                Icon(
                    image,
                    tint = color,
                    contentDescription = stringResource(textResourceId),
                    modifier = Modifier.height(24.dp)
                )
            }
            Text(stringResource(textResourceId), color = color)
        }
    }
}

@Composable
fun ConfirmDialog(
    text:String,
    onCancel: ()->Unit,
                  onConfirm: ()-> Unit)
{
    Dialog(onDismissRequest = onCancel) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colorScheme.background)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) { Text(text, fontSize = 24.sp, modifier = Modifier.padding(top = 10.dp)) }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                TextButton(
                    onClick = { onCancel() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text(stringResource(R.string.no))
                }
                TextButton(
                    onClick = { onConfirm() },
                    modifier = Modifier.padding(8.dp),
                ) {
                    Text(stringResource(R.string.yes))
                }
            }

        }
    }
}
