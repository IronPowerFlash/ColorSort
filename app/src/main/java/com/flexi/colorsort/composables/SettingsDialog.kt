package com.flexi.colorsort.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.flexi.colorsort.R
import com.flexi.colorsort.models.ColorSchemes
import com.flexi.colorsort.models.GameSettings


@Composable
fun SettingsDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (GameSettings) -> Unit,
    settings: GameSettings,
) {
    var containerCount by remember { mutableFloatStateOf(settings.containerCount.toFloat()) }
    var containerSize by remember { mutableFloatStateOf(settings.containerSize.toFloat()) }
    var colorCount by remember { mutableFloatStateOf(settings.colorCount.toFloat()) }
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(stringResource(R.string.container_count))
                    Text(containerCount.toInt().toString())
                }
                Slider(
                    value = containerCount,
                    onValueChange = { containerCount = it },
                    steps = 8,
                    valueRange = 1f..10f
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(stringResource(R.string.container_size))
                    Text(containerSize.toInt().toString())
                }
                Slider(
                    value = containerSize,
                    onValueChange = { containerSize = it },
                    steps = 8,
                    valueRange = 1f..10f
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(stringResource(R.string.color_count))
                    Text(colorCount.toInt().toString())
                }
                Slider(
                    value = colorCount,
                    onValueChange = { colorCount = it },
                    steps = 8,
                    valueRange = 1f..10f
                )
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
                                    containerCount.toInt(),
                                    containerSize.toInt(),
                                    colorCount.toInt(),
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

@Preview
@Composable
fun SettingsDialogPreview() {
    SettingsDialog(onConfirmation = {}, onDismissRequest = {}, settings = GameSettings())

}