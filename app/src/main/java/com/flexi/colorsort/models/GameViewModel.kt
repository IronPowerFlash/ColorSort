package com.flexi.colorsort.models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlin.math.roundToInt

class GameViewModel : ViewModel(){
    var settings:GameSettings by mutableStateOf(GameSettings())
    private var _containers = generateGame().toMutableStateList()

    var isSolved:Boolean by mutableStateOf(false)
        private set
    var moves:Int by mutableIntStateOf(0)
        private set
    val containers: List<BrickContainer>
        get() = _containers

    fun setContainerSelected(item: BrickContainer){
        if(isSolved){
            return
        }
        var currentlySelected = _containers.find{it.selected}
        var newSelected = _containers.find{it.id == item.id}

        if(currentlySelected == null)
        {
            newSelected?.let{container -> container.selected = true}
        }
        else if(newSelected != null && currentlySelected.id != newSelected.id){
            currentlySelected.selected = false
            newSelected.selected = true
            var anyMovesMade = false
            while(currentlySelected.canPop() && newSelected.canPush(settings.containerSize, currentlySelected.bricks[0])) {
                var brick = currentlySelected.bricks.removeAt(0)
                newSelected.bricks.add(0, brick)
                anyMovesMade = true
            }
            if(anyMovesMade)
            {
                moves++
            }
            _containers.forEach { container -> container.selected = false }
        }
        else{
            newSelected?.let {container -> container.selected = !container.selected }
        }
        checkIsSolved()
    }

    fun restartGame(){
        _containers = generateGame().toMutableStateList()
        moves = 0
        isSolved = false
    }

    private fun checkIsSolved()
    {
        isSolved =  _containers.all { container -> container.bricks.isEmpty() ||
                container.bricks.all{brick -> brick == container.bricks[0]} }
        Log.d("GameViewModel", "isSolved: $isSolved")
    }

    private fun generateGame():List<BrickContainer>{
        val tag = "generateGame"
        var totalNoContainers = settings.containerCount
        var colorUnits:MutableList<Color?> = mutableListOf()
        val colorMap = ColorSchemes.getColorScheme(settings.colorSchemeId).colorMap

        for(i in 1..settings.containerCount){
            var noColorUnits = settings.containerSize - 1 + Math.random().roundToInt()
            var colorChoice = i % settings.colorCount
            if (colorChoice < 1){
                colorChoice = i
            }

            var newUnits = Array<Color?>(noColorUnits) { colorMap[colorChoice] ?: Color.Yellow}
            colorUnits.addAll(newUnits)
        }
        Log.d(tag, "ColorUnits size: ${colorUnits.size}")
        var emptyUnitsCount = totalNoContainers * settings.containerSize - colorUnits.size
        Log.d(tag, "Empty units: $emptyUnitsCount")
        if(emptyUnitsCount > 0){
            colorUnits.addAll(Array<Color?>(emptyUnitsCount) { null })
        }

        colorUnits.shuffle()
        Log.d(tag, "ColorUnits size: ${colorUnits.size}")

        var containers: MutableList<BrickContainer> = mutableListOf()
        var brickCounter = 0;
        for (i in 0..totalNoContainers-1) {
            var startIndex = i*settings.containerSize
            if(startIndex < 0) {
                startIndex = 0
            }
            var endIndex = i * settings.containerSize + settings.containerSize
            Log.d(tag, "startIndex: $startIndex, endIndex: $endIndex")
            var units = colorUnits.slice(IntRange(startIndex, endInclusive = endIndex-1))
            var colored = units.filter{ color -> color != null} as List<Color>
            brickCounter+= colored.size
            containers.add(BrickContainer(id = i+1, initialBricks = colored, selected = false))
        }
        containers.add(BrickContainer(id = totalNoContainers +1, initialBricks = emptyList(), selected = false))
        containers.add(BrickContainer(id = totalNoContainers +2, initialBricks = emptyList(), selected = false))

        return containers
    }

    fun updateSettings(value: GameSettings) {
        if(settings.colorCount != value.colorCount
            || settings.containerCount != value.containerCount
            || settings.containerSize != value.containerSize
            || settings.colorSchemeId != value.colorSchemeId)
        {
            settings = value
            restartGame()
        }
    }
}