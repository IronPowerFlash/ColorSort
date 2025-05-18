package com.flexi.colorsort.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.flexi.colorsort.R
import com.flexi.colorsort.models.ColorSchemes
import com.flexi.colorsort.models.GameSettings
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (GameSettings) -> Unit,
    settings: GameSettings,
) {
    var containerCount by remember { mutableIntStateOf(settings.containerCount) }
    var containerSize by remember { mutableIntStateOf(settings.containerSize) }
    var colorCount by remember { mutableIntStateOf(settings.colorCount) }
    var colorSchemeExpanded by remember { mutableStateOf(false) }
    var colorSchemeId by remember { mutableIntStateOf(settings.colorSchemeId) }
    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(20.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    stringResource(R.string.settings),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )
                IntegerSliderField(1, 10, R.string.container_count, settings.containerCount,
                    {newValue -> containerCount = newValue})

                IntegerSliderField(1, 10, R.string.container_size, settings.containerSize,
                    {newValue -> containerSize = newValue})

                IntegerSliderField(1, 10, R.string.color_count, settings.colorCount,
                    onValueChanged = {newValue -> colorCount = newValue })

                Text(stringResource(R.string.color_scheme))
                TextButton(onClick = { colorSchemeExpanded = !colorSchemeExpanded }
                ) {
                    Text("Color scheme ${colorSchemeId + 1}")
                }
                Box {
                    DropdownMenu(
                        expanded = colorSchemeExpanded,
                        onDismissRequest = { colorSchemeExpanded = false }) {
                        ColorSchemes.getColorSchemeIds().forEach { option ->
                            DropdownMenuItem(
                                text = { Text("Color scheme ${option + 1}") },
                                onClick = {
                                    colorSchemeId = option
                                    colorSchemeExpanded = false
                                })
                        }

                    }
                }
                Text("Changing settings will start a new game",
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { onDismissRequest() }, Modifier.padding(8.dp)) {
                        Text(stringResource(R.string.btn_Close))
                    }
                    TextButton(
                        onClick = {
                            onConfirmation(
                                GameSettings(
                                    containerCount,
                                    containerSize,
                                    colorCount,
                                    colorSchemeId
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
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntegerSliderField(
    minValue:Int,
    maxValue:Int,
    labelResourceId:Int,
    initialValue:Int,
    onValueChanged: (value:Int)->Unit)
{

    var currentValue by remember{ mutableFloatStateOf(initialValue.toFloat()) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(stringResource(labelResourceId))
    }
    val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
    Slider(
        value = currentValue,
        onValueChange = { currentValue = it },
        steps = maxValue - 2,
        onValueChangeFinished = { onValueChanged(currentValue.roundToInt())},
        thumb = {
            val shape = CircleShape
            Column(
                modifier = Modifier
                    .size(30.dp)
                    .hoverable(interactionSource = interactionSource)
                    .shadow(0.dp, CircleShape, clip = false)
                    .background(MaterialTheme.colorScheme.primary, shape),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                Text("${currentValue.roundToInt()}",
                    color = MaterialTheme.colorScheme.onPrimary)
            }

        },
        valueRange = minValue.toFloat()..maxValue.toFloat()
    )

}

@Preview
@Composable
fun SettingsDialogPreview() {
    val previewSettings = GameSettings(9, 8, 7, 1)
    SettingsDialog(onConfirmation = {}, onDismissRequest = {}, settings = previewSettings)

}