package com.flexi.colorsort.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
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
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    stringResource(R.string.settings),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                IntegerSliderField(
                    1, 10, R.string.container_count, settings.containerCount,
                    { newValue -> containerCount = newValue })
                IntegerSliderField(
                    1, 10, R.string.container_size, settings.containerSize,
                    { newValue -> containerSize = newValue })

                IntegerSliderField(
                    1, 10, R.string.color_count, settings.colorCount,
                    onValueChanged = { newValue -> colorCount = newValue })
                Text(stringResource(R.string.color_scheme), modifier = Modifier.fillMaxWidth(0.5f))
                Row( Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically){

                TextButton(onClick = { colorSchemeExpanded = !colorSchemeExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(ColorSchemes.getColorScheme(colorSchemeId).name)
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    DropdownMenu(
                        expanded = colorSchemeExpanded,
                        onDismissRequest = { colorSchemeExpanded = false },
                        modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(15.dp)){
                        ColorSchemes.getColorSchemeIds().forEach { option ->
                                ColorSchemeSwatch(option, { id ->
                                    colorSchemeId = id
                                    colorSchemeExpanded = false
                                })
                            }
                        }

                    }
                }
                }
                Text(
                    "Changing settings will start a new game",
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
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

@Composable
fun ColorSchemeSwatch(colorSchemeId:Int, onClick:(Int)->Unit){
    var colorScheme = ColorSchemes.getColorScheme(colorSchemeId)
    Column( modifier = Modifier.fillMaxWidth()
        .clickable( onClick ={ onClick(colorSchemeId)})) {
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
        Text(stringResource(labelResourceId), modifier= Modifier.fillMaxWidth(0.3f))

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
    val previewSettings = GameSettings(9, 8, 7, 1)
    SettingsDialog(onConfirmation = {}, onDismissRequest = {}, settings = previewSettings)

}