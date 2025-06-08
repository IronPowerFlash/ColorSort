package com.flexi.colorsort.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.flexi.colorsort.R
import com.flexi.colorsort.models.ColorSchemes
import com.flexi.colorsort.models.GameSettings
import com.flexi.colorsort.models.IconStore
import kotlin.math.roundToInt


@Composable
fun SettingsScreen(
    onCancel: () -> Unit,
    onSave: (GameSettings) -> Unit,
    settings: GameSettings,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            IconActionButton(
                icon = Icons.Outlined.Clear,
                textResourceId = R.string.btn_Close,
                onClick = onCancel
            )
        }
        SettingsContent(onCancel, onSave, settings)

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    onCancel: () -> Unit,
    onSave: (GameSettings) -> Unit,
    settings: GameSettings,
) {
    var containerCount by remember { mutableFloatStateOf(settings.containerCount.toFloat() + 2) }
    var containerSize by remember { mutableIntStateOf(settings.containerSize) }
    var colorCount by remember { mutableFloatStateOf(settings.colorCount.toFloat()) }
    var colorSchemeExpanded by remember { mutableStateOf(false) }
    var colorSchemeId by remember { mutableIntStateOf(settings.colorSchemeId) }
    var colorBlindMode by remember { mutableStateOf(settings.colorBlindMode) }
    var selectedIconIdsState = remember { settings.selectedIconIds.toMutableStateList() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            stringResource(R.string.settings),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.container_count),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(0.3f)
            )

            val containerSliderInteractionSource: MutableInteractionSource =
                remember { MutableInteractionSource() }

            Slider(
                value = containerCount,
                onValueChange = { containerCount = it },
                steps = 8,
                onValueChangeFinished = {

                    if (colorCount > containerCount) {
                        colorCount = containerCount
                    }
                },
                modifier = Modifier.padding(0.dp),
                thumb = {
                    val shape = CircleShape
                    Column(
                        modifier = Modifier
                            .size(30.dp)
                            .hoverable(interactionSource = containerSliderInteractionSource)
                            .shadow(2.dp, CircleShape, clip = true)
                            .background(MaterialTheme.colorScheme.primary, shape),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    )
                    {
                        Text(
                            "${containerCount.roundToInt()}",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                },
                valueRange = 3f..12f
            )
        }
        IntegerSliderField(
            1, 10, R.string.container_size, settings.containerSize,
            onValueChanged = { newValue -> containerSize = newValue }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.color_count),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(0.3f)
            )

            val containerSliderInteractionSource: MutableInteractionSource =
                remember { MutableInteractionSource() }

            Slider(
                value = colorCount,
                onValueChange = { colorCount = it },
                steps = 7,
                onValueChangeFinished = {

                    if (colorCount > containerCount) {
                        containerCount = colorCount
                    }
                },
                modifier = Modifier.padding(0.dp),
                thumb = {
                    val shape = CircleShape
                    Column(
                        modifier = Modifier
                            .size(30.dp)
                            .hoverable(interactionSource = containerSliderInteractionSource)
                            .shadow(2.dp, CircleShape, clip = true)
                            .background(MaterialTheme.colorScheme.primary, shape),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    )
                    {
                        Text(
                            "${colorCount.roundToInt()}",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                },
                valueRange = 2f..10f
            )
        }
        Text(stringResource(R.string.color_scheme), modifier = Modifier.fillMaxWidth(0.5f))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextButton(
                onClick = { colorSchemeExpanded = !colorSchemeExpanded },

                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    ColorSchemes.getColorScheme(colorSchemeId).name,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                DropdownMenu(
                    expanded = colorSchemeExpanded,
                    onDismissRequest = { colorSchemeExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        ColorSchemes.getColorSchemeIds().forEach { option ->
                            ColorSchemeSwatch(
                                colorSchemeId = option,
                                onClick = { id ->
                                colorSchemeId = id
                                colorSchemeExpanded = false
                            })
                        }
                    }

                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.colorblind_mode),
                color = MaterialTheme.colorScheme.onBackground,
            )
            Checkbox(checked = colorBlindMode, onCheckedChange = { colorBlindMode = it })
        }
        Text(
            "Changing settings will start a new game",
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.onBackground,
        )

        IconSetSelector(selectedIconIdsState)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { onCancel() }, Modifier.padding(8.dp)) {
                Text(stringResource(R.string.btn_Close))
            }
            TextButton(
                onClick = {
                    onSave(
                        GameSettings(
                            containerCount.roundToInt() - 2,
                            containerSize,
                            colorCount.roundToInt(),
                            colorSchemeId,
                            colorBlindMode,
                            selectedIconIdsState.toList()
                        )
                    )
                },
                Modifier.padding(8.dp)
            ) {
                Text(stringResource(R.string.btn_Save))
            }
        }
    }

}

@Composable
fun ColorSchemeSwatch(colorSchemeId: Int, onClick: (Int) -> Unit) {
    var colorScheme = ColorSchemes.getColorScheme(colorSchemeId)

    Column(
        modifier = Modifier
            .fillMaxWidth()

            .background(color = MaterialTheme.colorScheme.background)
            .clickable(onClick = { onClick(colorSchemeId) })
    ) {
        Text(colorScheme.name, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Row(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color1)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color2)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color3)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color4)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color5)
            )
        }
        Row(Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color6)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color7)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color8)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color9)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .background(colorScheme.color10)
            )
        }
    }
}

@Composable
fun IconSetSelector(selectedIconIds: MutableList<Int>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(stringResource(R.string.select_icons_instruction), color = MaterialTheme.colorScheme.onBackground)
    }

    val scrollState = rememberScrollState(initial = 0)
    FlowRow(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(200.dp)
                .verticalScroll(scrollState),
        maxItemsInEachRow = 10,
        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally)
    ) {

        for (icon in IconStore.iconMap) {
            SelectableIcon(
                iconResource = icon.value,
                isSelected = selectedIconIds.indexOf(icon.key) >= 0,
                description = "",
                onClick = {
                    val currentCount = selectedIconIds.size
                    val selected = selectedIconIds.indexOf(icon.key) >= 0
                    if (selected) {
                        selectedIconIds.remove(icon.key)
                    } else if (currentCount < 10) {
                        selectedIconIds.add(icon.key)
                    }
                })
        }
    }

}

@Composable
fun SelectableIcon(
    iconResource: Int,
    isSelected: Boolean,
    description: String,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(if (isSelected) Color.Black else Color.Gray)
            .padding(3.dp)
            .fillMaxWidth(0.1f)
            .clickable(onClick = { onClick(iconResource) }),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(iconResource),
            tint = if (isSelected) Color.Magenta else Color.Black,
            contentDescription = description
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntegerSliderField(
    minValue: Int,
    maxValue: Int,
    labelResourceId: Int,
    initialValue: Int,
    onValueChanged: (value: Int) -> Unit,
) {
    var currentValue by remember { mutableFloatStateOf(initialValue.toFloat()) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(labelResourceId), modifier = Modifier.fillMaxWidth(0.3f))

        val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }

        Slider(
            value = currentValue,
            onValueChange = { currentValue = it },
            steps = maxValue - 2,
            onValueChangeFinished = { onValueChanged(currentValue.roundToInt()) },
            modifier = Modifier.padding(0.dp),
            thumb = {
                val shape = CircleShape
                Column(
                    modifier = Modifier
                        .size(30.dp)
                        .hoverable(interactionSource = interactionSource)
                        .shadow(2.dp, CircleShape, clip = true)
                        .background(MaterialTheme.colorScheme.primary, shape),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Text(
                        "${currentValue.roundToInt()}",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

            },
            valueRange = minValue.toFloat()..maxValue.toFloat()
        )
    }
}

@Preview
@Composable
fun SettingsDialogPreview() {
    val previewSettings = GameSettings(9, 8, 7, 1, selectedIconIds = listOf(1, 2, 3))
    SettingsScreen(settings = previewSettings, onSave = {}, onCancel = {})

}