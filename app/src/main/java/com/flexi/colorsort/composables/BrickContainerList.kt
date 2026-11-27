package com.flexi.colorsort.composables


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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.core.graphics.ColorUtils
import androidx.window.core.layout.WindowSizeClass
import com.flexi.colorsort.extensions.glowBorder
import com.flexi.colorsort.models.BrickContainer
import com.flexi.colorsort.models.ColorSchemes
import com.flexi.colorsort.models.IconStore
import com.flexi.colorsort.ui.theme.BrickColorScheme
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
    colorScheme: BrickColorScheme,
    renderIcons: Boolean,
    colorPickMap: Map<Int, Int>,
    iconIds: List<Int> = emptyList<Int>(),
) {
    var rowCount = 1
    if (windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
        rowCount = 1
    } else if (windowSizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND))
        rowCount = 2

    var maxItems = ceil(containers.size / rowCount.toFloat()).roundToInt()
    var rowSize by remember { mutableStateOf(Size.Zero) }
    val spacing = 10

    FlowRow(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .onGloballyPositioned { coordinates ->
                rowSize = coordinates.size.toSize()
            },
        horizontalArrangement = Arrangement.spacedBy(spacing.dp, Alignment.CenterHorizontally),
//        verticalArrangement = Arrangement.spacedBy(spacing.dp, Alignment.CenterVertically),
        //horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.SpaceEvenly,
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
                maxColumnHeight = columnHeight,
                colorScheme = colorScheme,
                renderIcons = renderIcons,
                iconIds = iconIds,
                colorPickMap = colorPickMap,
            )
        }
    }
}

@Composable
fun BrickContainerComponent(
    bricks: List<Int>,
    isSelected: Boolean,
    onContainerSelected: () -> Unit,
    modifier: Modifier = Modifier,
    maxBricks: Int,
    maxColumnWidth: Dp,
    maxColumnHeight: Dp,
    colorScheme: BrickColorScheme,
    renderIcons: Boolean,
    iconIds: List<Int>,
    colorPickMap: Map<Int, Int>
) {
    var borderWidth = 2
    val spacing = 1

    var columnHeight = maxColumnHeight - 30.dp
    if (columnHeight / maxBricks > 80.dp) {
        columnHeight = 80.dp * maxBricks + (spacing * 2).dp
    }
    var maxBrickHeight = LocalDensity.current.run {
        (1f / maxBricks * (columnHeight.toPx())).toDp()
    } - (spacing * 1.25).dp

    var columnWidth = maxColumnWidth - (spacing * 4).dp - (borderWidth * 2).dp
    if (columnWidth > maxBrickHeight) {
        columnWidth = maxBrickHeight
    }
    if (maxBrickHeight > columnWidth) {
        columnHeight = maxBrickHeight * maxBricks
        maxBrickHeight = LocalDensity.current.run {
            (1f / maxBricks * (columnHeight.toPx())).toDp()
        } - (spacing * 1.25).dp
    }

    Column(
        modifier = Modifier
            .width(columnWidth + (spacing * 2).dp + (borderWidth * 2).dp)
            .height(columnHeight)
            .background(if (isSelected) Color.Gray else Color.Black)
            .glowBorder(Color.Cyan, 10.dp, if (isSelected) 6.dp else 0.dp)
            .border(borderWidth.dp, if (isSelected) Color.Cyan else Color.Gray)
            .padding(bottom = (spacing).dp)
            .clickable(onClick = { onContainerSelected() }),
        verticalArrangement = Arrangement.spacedBy(spacing.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {



        for (brick in bricks) {
//            val icon = iconIds?.size?.let { if(it > 0 && it >= brick) IconStore.iconMap[iconIds[brick]] else null }

            val icon = IconStore.iconMap[iconIds[brick]]
            ColorBrick(brick, modifier, maxBrickHeight, columnWidth, colorScheme, renderIcons, icon, colorPickMap)
        }
    }
}



@Composable
fun ColorBrick(
    colorId: Int,
    modifier: Modifier = Modifier,
    height: Dp,
    width: Dp,
    colorScheme: BrickColorScheme,
    renderIcon:Boolean,
    iconResourceId: Int? = null,
    colorPickMap: Map<Int, Int>
) {
    val mappedColorId = colorPickMap.getOrDefault(colorId, colorId);
    val color = colorScheme.colorMap[mappedColorId]?:Color.Green
    val description = when (colorId) {
        1 -> "a"
        2 -> "b"
        3 -> "c"
        4 -> "d"
        5 -> "e"
        6 -> "f"
        7 -> "g"
        8 -> "h"
        9 -> "i"
        10 -> "j"
        11 -> "k"
        12 -> "l"
        13 -> "m"
        14 -> "n"
        15 -> "o"
        16 -> "p"
        17 -> "q"
        18 -> "r"
        19 -> "s"
        else -> "t"

    }
    Box(
        modifier = modifier
            .background(color)
            .height(height)
            .width(width)
            .padding(3.dp),
        contentAlignment = Alignment.Center
    ) {
        if(renderIcon && iconResourceId != null) {
            BrickShape(iconResourceId, color, description)
        }
    }
}

@Composable
fun BrickShape(
    iconResourceId: Int,
    brickColor: Color,
    description: String) {
    val contrastMap = mapOf<Color, Double>(
        Pair<Color, Double>(Color.White, ColorUtils.calculateContrast(Color.White.toArgb(), brickColor.toArgb())),
        Pair<Color, Double>(Color.Gray, ColorUtils.calculateContrast(Color.Gray.toArgb(), brickColor.toArgb())),
        Pair<Color, Double>(Color.Black, ColorUtils.calculateContrast(Color.Black.toArgb(), brickColor.toArgb()))
    )
    val iconColor = contrastMap.maxBy { it.value }.key
    Icon(
        painterResource(iconResourceId),
        tint = iconColor,
        contentDescription = description,
    )
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
        windowSizeClass = windowSizeClass,
        colorScheme = ColorSchemes.getColorScheme(2),
        renderIcons = true,
        colorPickMap = mapOf(1 to 1, 2 to 3, 3 to 5, 4 to 7)
    )

}

private fun getPreviewContainers(containerCount: Int, brickCount: Int) = List(containerCount) { i ->
    BrickContainer(
        i,
        List(if (i % 3 == 0) brickCount else brickCount - 2) { c -> if (c % 3 == 0) 1 else if (c % 2 == 0) 2 else 3 },
        i == 2
    )
}