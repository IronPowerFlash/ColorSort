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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.window.core.layout.WindowSizeClass
import com.flexi.colorsort.extensions.glowBorder
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
    windowSizeClass: WindowSizeClass,
) {
    var rowCount = 1
    if (windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        rowCount = 1
    } else if (windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND))
        rowCount = 2

    var maxItems = ceil(containers.size / rowCount.toFloat()).roundToInt()
    Log.d("BrickContainerList", "containerCount: ${containers.size}, maxItems: $maxItems")
    var rowSize by remember { mutableStateOf(Size.Zero) }
    val spacing = 20
    Log.d("BrickContainerList", "rowCount: $rowCount")

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .onGloballyPositioned { coordinates ->
                rowSize = coordinates.size.toSize()
            },
        horizontalArrangement = Arrangement.spacedBy(spacing.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(spacing.dp, Alignment.CenterVertically),
        maxItemsInEachRow = maxItems) {
        for (container in containers) {
            var maxColumnWidth = LocalDensity.current.run {
                (rowSize.width / maxItems).toDp()
            } - spacing.dp
            var columnHeight = LocalDensity.current.run {
                (rowSize.height / rowCount).toDp()
            } - (spacing * (rowCount - 1)).dp
            BrickContainerComponent(
                bricks = container.bricks,
                isSelected = container.selected,
                onContainerSelected = { onContainerSelected(container) },
                maxBricks = maxBricks,
                maxColumnWidth = maxColumnWidth,
                maxColumnHeight = columnHeight
            )
        }
    }
}

@Composable
fun BrickContainerComponent(
    bricks: List<Color>,
    isSelected: Boolean,
    onContainerSelected: () -> Unit,
    modifier: Modifier = Modifier,
    maxBricks: Int,
    maxColumnWidth: Dp,
    maxColumnHeight: Dp,
) {
    var borderWidth = 2
    val spacing = 3

    var columnHeight = maxColumnHeight - 30.dp
    if (columnHeight / maxBricks > 80.dp) {
        columnHeight = 80.dp * maxBricks + (spacing * 2).dp
    }
    var maxBrickHeight = LocalDensity.current.run {
        (1f / maxBricks * (columnHeight.toPx())).toDp()
    } - (spacing * 1.25).dp

    var columnWidth = maxColumnWidth - (spacing * 4).dp - (borderWidth * 2).dp
    Log.d(
        "BrickContainer",
        "Initial calc. columnHeight: $columnHeight, columnWidth: $columnWidth, maxBrickHeight: $maxBrickHeight"
    )

    if (columnWidth > maxBrickHeight) {
        columnWidth = maxBrickHeight
        Log.d(
            "BrickContainer",
            "columnWidth > maxBrickHeight. columnHeight: $columnHeight, columnWidth: $columnWidth, maxBrickHeight: $maxBrickHeight"
        )
    }
    if (maxBrickHeight > columnWidth) {
        columnHeight = maxBrickHeight * maxBricks
        maxBrickHeight = LocalDensity.current.run {
            (1f / maxBricks * (columnHeight.toPx())).toDp()
        } - (spacing * 1.25).dp
        Log.d(
            "BrickContainer",
            "maxBrickHeight > columnWidth. columnHeight: $columnHeight, columnWidth: $columnWidth, maxBrickHeight: $maxBrickHeight"
        )
    }
    Column(
        modifier = Modifier
            .width(columnWidth + (spacing * 2).dp + (borderWidth * 2).dp)
            .height(columnHeight)
            .background(if (isSelected) Color.Gray else Color.Black)
            .glowBorder(Color.White, 10.dp, if (isSelected) 5.dp else 0.dp)
            .border(borderWidth.dp, if (isSelected) Color.White else Color.Gray)
            .padding(bottom = (spacing).dp)
            .clickable(onClick = { onContainerSelected() }),
        verticalArrangement = Arrangement.spacedBy(spacing.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (brick in bricks) {
            ColorBrick(brick, modifier, maxBrickHeight, columnWidth)
        }
    }
}

@Composable
fun ColorBrick(
    color: Color,
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
) {
    Box(
        modifier = modifier
            .background(color)
            .height(height)
            .width(width)
    ) {}
}

@Preview
@PreviewScreenSizes
@Composable
fun BrickContainerListPreview() {
    val configuration = LocalConfiguration.current
    val windowSizeClass = WindowSizeClass.compute(
        dpHeight = configuration.screenHeightDp.toFloat(),
        dpWidth = configuration.screenWidthDp.toFloat(),
    )
    val containerCount = 8
    val brickCount = 4
    BrickContainerList(
        getPreviewContainers(containerCount, brickCount),
        onContainerSelected = {},
        maxBricks = brickCount,
        windowSizeClass = windowSizeClass
    )

}

private fun getPreviewContainers(containerCount: Int, brickCount: Int) = List(containerCount) { i ->
    BrickContainer(
        i,
        List(if (i % 3 == 0) brickCount else brickCount - 2) { c -> if (c % 3 == 0) Color.Cyan else if (c % 2 == 0) Color.Yellow else Color.Magenta },
        i == 2
    )
}