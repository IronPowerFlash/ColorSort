package com.flexi.colorsort.composables

import android.R.attr.fontWeight
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.flexi.colorsort.models.BrickContainer
import com.flexi.colorsort.models.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.flexi.colorsort.models.GameSettings
import org.intellij.lang.annotations.JdkConstants
import kotlin.math.ceil
import kotlin.math.roundToInt
import kotlin.times


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
                        "Wohoo! You've solved it!",
                        fontSize = 48.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 48.sp
                    )
                    Button(onClick = {gameViewModel.restartGame()}) {
                        Text("Restart")
                    }
                }
            }
        } else {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {settingsDialogOpen.value = true}) {
                    Text("Settings")
                }
//                Button(onClick = {}) {
//                    Text("Undo")
//                }
            }
//            Column(modifier = Modifier.fillMaxHeight(0.8F)){
            BrickContainerList(
                containers = gameViewModel.containers,
                maxBricks = gameViewModel.maxBricks,
                onContainerSelected = { container -> gameViewModel.setContainerSelected(container) },
                modifier = modifier
            )
//            }

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
                        "Level", textAlign = TextAlign.Center,
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
                        "Moves", textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BrickContainerList(
    containers: List<BrickContainer>,
    onContainerSelected: (BrickContainer) -> Unit,
    maxBricks: Int,
    modifier: Modifier = Modifier,
) {

    var brickSize = 40
    if(maxBricks > 8){
        brickSize = 35
    }
    var rowHeight = maxBricks * (brickSize + 2) + (maxBricks - 1) * 5
    Log.d("BrickContainerList", "rowHeight: $rowHeight")
    var maxItems = if(containers.size <= 5) 5 else ceil(containers.size / 2.0).roundToInt()


    FlowRow (modifier = Modifier
        .fillMaxWidth()
//        .height((rowHeight*2 + 20).dp)
        .background(Color.Gray)
        .wrapContentHeight(align = Alignment.CenterVertically, unbounded = true),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        maxItemsInEachRow = maxItems){
        for(container in containers){
            BrickContainerComponent(
                bricks = container.bricks,
                isSelected = container.selected,
                onContainerSelected = { onContainerSelected(container) },
                height = rowHeight,
                brickSize = brickSize
            )
        }
    }
//    LazyRow(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(rowHeight.dp),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//    ) {
//        items(
//            containers,
//            key = { container -> container.id }) { container ->
//            BrickContainerComponent(
//                bricks = container.bricks,
//                isSelected = container.selected,
//                onContainerSelected = { onContainerSelected(container) }
//            )
//        }
//    }
}

@Composable
fun BrickContainerComponent(
    bricks: List<Color>,
    isSelected: Boolean,

    onContainerSelected: () -> Unit,
    height:Int,
    brickSize:Int,
    modifier: Modifier = Modifier
) {
    var borderWidth = 1
    if (isSelected) {
        borderWidth = 3
    }

    Column(
        modifier = modifier
            .height(height.dp)
            .width((brickSize + 10).dp)
            .background(Color.Black)
            .border(borderWidth.dp, Color.White)
            .padding(5.dp)
            .clickable(onClick = { onContainerSelected() }),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Bottom)
    ) {
        for (brick in bricks) {
            ColorBrick(brick, modifier, brickSize)
        }
    }
}

@Composable
fun ColorBrick(color: Color, modifier: Modifier = Modifier, size:Int) {
    Box(
        modifier = modifier
            .background(color)
            .border(1.dp, Color.White)
            .size(size.dp, size.dp)
    ) {}
}

@Composable
fun SettingsDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (GameSettings)-> Unit,
    settings: GameSettings
){
    var containerCount by remember{mutableStateOf(settings.containerCount.toFloat())}
    var containerSize by remember {mutableStateOf(settings.containerSize.toFloat())}
    var colorCount by remember {mutableStateOf(settings.colorCount.toFloat())}
    Dialog(onDismissRequest = {}){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ){
        Column(modifier = Modifier.fillMaxSize()){
            Text("Settings", fontSize = 16.sp, textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp))
            Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)){
                Text("Container count")
                Text(containerCount.toInt().toString())
            }
            Slider(
                value = containerCount,
                onValueChange = {containerCount = it },
                steps = 8,
                valueRange = 1f..10f
                )

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)){
                Text("Container size")
                Text(containerSize.toInt().toString())
            }
            Slider(
                value = containerSize,
                onValueChange = {containerSize = it },
                steps = 8,
                valueRange = 1f..10f
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)){
                Text("Color count")
                Text(colorCount.toInt().toString())
            }
            Slider(
                value = colorCount,
                onValueChange = {colorCount = it },
                steps = 8,
                valueRange = 1f..10f
            )
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =  Arrangement.Center){
                TextButton(onClick = {onDismissRequest() }, Modifier.padding(8.dp)) {
                    Text("Close")
                }
                TextButton(onClick = {
                    onConfirmation(GameSettings(containerCount.toInt(), containerSize.toInt(), colorCount.toInt())) },
                    Modifier.padding(8.dp)) {
                    Text("Save")
                }
            }
        }
        }
    }
}

@Preview
@Composable
fun SettingsDialogPreview(){
    SettingsDialog(onConfirmation = {}, onDismissRequest = {}, settings = GameSettings())

}