package com.flexi.colorsort.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flexi.colorsort.models.BrickContainer
import kotlin.math.ceil
import kotlin.math.roundToInt


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
        brickSize = 25
    }
    var rowHeight by remember {mutableIntStateOf(0)}
    rowHeight = maxBricks * (brickSize + 2) + (maxBricks - 1) * 5
    Log.d("BrickContainerList", "rowHeight: $rowHeight")
    var maxItems = if(containers.size <= 5) 5 else ceil(containers.size / 2.0).roundToInt()


    FlowRow (modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(align = Alignment.CenterVertically, unbounded = true),
        horizontalArrangement = Arrangement.SpaceEvenly,
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

@Preview
@Composable
fun BrickContainerListPreview(){
    
    val containerCount = 10
    val brickCount = 10
    Box(Modifier.height(1000.dp).width(500.dp)) {
        BrickContainerList(
            getPreviewContainers(containerCount, brickCount),
            onContainerSelected = {},
            maxBricks = brickCount
        )
    }

}

private fun getPreviewContainers(containerCount:Int, brickCount:Int) = List(containerCount){ i ->
    BrickContainer(i,
    List(brickCount) {c-> if(c % 3 == 0) Color.Cyan else if (c% 2 == 0) Color.Yellow else Color.Magenta}, false)
}