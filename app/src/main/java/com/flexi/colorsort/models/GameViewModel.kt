package com.flexi.colorsort.models

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flexi.colorsort.data.SettingsRepository
import com.flexi.colorsort.data.UserAppSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

private const val TAG: String = "GameViewModel"

@HiltViewModel
class GameViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository, // Injecting SettingsRepository
) : ViewModel() {
    private var settingsInitialized:Boolean by mutableStateOf(false)
    var settings: GameSettings by mutableStateOf(GameSettings())
        private set
    private var _originalContainers = emptyList<BrickContainer>()
    private var _containers = _originalContainers.map { it.copy() }.toMutableStateList()
    private var _containerHistory = emptyList<List<BrickContainer>>().toMutableList()

    init {
        Log.d(TAG, "running init")
        if (!settingsInitialized) {
            viewModelScope.launch {
                settingsRepository.settingsFlow.collect { userAppSettings ->
                    Log.d(TAG, "userAppSettings in repo: containerCount: ${userAppSettings.containerCount}, containerSize: ${userAppSettings.containerSize}")
                    settings = mapToGameSettings(userAppSettings)
                    settingsInitialized = true
                    newGame()
                }
            }
            settingsInitialized = true
        }
    }

    private val maxUndos: Int = 5
    var isSolved: Boolean by mutableStateOf(false)
        private set
    var moves: Int by mutableIntStateOf(0)
        private set
    var canUndo: Boolean by mutableStateOf(false)
        private set
    val containers: List<BrickContainer>
        get() = _containers
    var gameIconIds = emptyList<Int>().toMutableStateList()
        private set

    fun setContainerSelected(item: BrickContainer) {
        if (isSolved) {
            return
        }
        var currentlySelected = _containers.find { it.selected }
        var newSelected = _containers.find { it.id == item.id }

        if (currentlySelected == null) {
            newSelected?.let { container -> container.selected = true }
        } else if (newSelected != null && currentlySelected.id != newSelected.id) {
            currentlySelected.selected = false
            newSelected.selected = true


            if (currentlySelected.canPop() && newSelected.canPush(
                    settings.containerSize,
                    currentlySelected.bricks[0]
                )
            ) {

                addContainersToHistory()
                while (currentlySelected.canPop() && newSelected.canPush(
                        settings.containerSize,
                        currentlySelected.bricks[0]
                    )
                ) {
                    var brick = currentlySelected.bricks.removeAt(0)
                    newSelected.bricks.add(0, brick)
                }
                moves++
            }
            _containers.forEach { container -> container.selected = false }
        } else {
            newSelected?.let { container -> container.selected = !container.selected }
        }
        checkIsSolved()
    }

    fun newGame() {
        Log.d(TAG, "newGame called")
        _originalContainers = generateGame()
        _containers = _originalContainers.map { it.copy() }.toMutableStateList()
        _containerHistory.clear()
        viewModelScope.launch {
            settingsRepository.settingsFlow.collect { userAppSettings ->
                Log.d(
                    TAG,
                    "userAppSettings in repo: containerCount: ${userAppSettings.containerCount}, containerSize: ${userAppSettings.containerSize}"
                )
                settings = mapToGameSettings(userAppSettings)
            }
        }
        updateGameIcons()
        moves = 0
        isSolved = false
        canUndo = false
    }

    fun restart() {
        if (_originalContainers.isNotEmpty()) {
            _containers.clear()
            _containers.addAll(_originalContainers.map { it.copy() })
        }
        moves = 0
        isSolved = false
        canUndo = false
    }

    fun undo() {
        if (_containerHistory.isNotEmpty()) {
            var previousContainers = _containerHistory.removeAt(_containerHistory.lastIndex)
            _containers.clear()
            _containers.addAll(previousContainers)
            _containers.forEach { container -> container.selected = false }
        }
        canUndo = !_containerHistory.isEmpty()
    }

    private fun addContainersToHistory() {
        if (_containerHistory.size >= maxUndos) {
            while (_containerHistory.size >= maxUndos) {
                _containerHistory.removeAt(0)
            }
        }
        _containerHistory.add(_containers.map { it.copy() }.toList())
        canUndo = true
    }

    private fun updateGameIcons() {
        gameIconIds.clear()
        gameIconIds = settings.selectedIconIds.toMutableStateList()
        if (settings.colorCount > gameIconIds.size) {
            gameIconIds.addAll(IconStore.getRandomIconIds(settings.colorCount - gameIconIds.size, gameIconIds.toList()))
        }
    }

    private fun checkIsSolved() {
        isSolved = _containers.all { container ->
            container.bricks.isEmpty() ||
                    container.bricks.all { brick -> brick == container.bricks[0] }
        }
    }

    fun updateSettings(value: GameSettings) {
        var updateGameIcons = false
        if (settings.colorSchemeId != value.colorSchemeId
            || settings.colorBlindMode != value.colorBlindMode
            || settings.selectedIconIds != value.selectedIconIds
        ) {
            Log.d("updateSettings", "update game icons")
            updateGameIcons = true
        }
        settings = GameSettings(
            colorSchemeId = value.colorSchemeId,
            colorBlindMode = value.colorBlindMode,
            selectedIconIds = value.selectedIconIds,
            colorCount = settings.colorCount,
            containerCount = settings.containerCount,
            containerSize = settings.containerSize
        )

        if(updateGameIcons){
            updateGameIcons()
        }

        viewModelScope.launch {
            settingsRepository.updateSettings(value)
        }
    }

    private fun mapToGameSettings(userAppSettings: UserAppSettings): GameSettings {
        Log.d(
            TAG, "mapping GameSettings from {containerSize: ${userAppSettings.containerSize}, " +
                    "containerCount: ${userAppSettings.containerCount}, " +
                    "colorCount: ${userAppSettings.colorCount}, " +
                    "colorBlindMode: ${userAppSettings.colorblindMode}, " +
                    "colorSchemeId: ${userAppSettings.colorSchemeId}, " +
                    "selectedIcons: ${userAppSettings.selectedIconsList.joinToString(", ")}}"
        )
        var gameSettings = GameSettings(
            containerSize = if (userAppSettings.containerSize == 0) 3 else userAppSettings.containerSize,
            containerCount = if (userAppSettings.containerCount == 0) 2 else userAppSettings.containerCount,
            colorCount = if (userAppSettings.colorCount == 0) 3 else userAppSettings.colorCount,
            colorBlindMode = userAppSettings.colorblindMode,
            colorSchemeId = if (userAppSettings.colorSchemeId == 0) 1 else userAppSettings.colorSchemeId,
            selectedIconIds = userAppSettings.selectedIconsList
        )
        return gameSettings
    }

    private fun generateGame(): List<BrickContainer> {
        var totalNoContainers = settings.containerCount
        var colorUnits: MutableList<Int> = mutableListOf()
//        Log.d("generateGame", "totalNoContainers: $totalNoContainers")
        for (i in 1..totalNoContainers) {
            var noColorUnits = settings.containerSize - 1 + Math.random().roundToInt()
            var colorChoice = i % settings.colorCount
            colorUnits.addAll(Array<Int>(noColorUnits) { colorChoice })
        }
        var emptyUnitsCount = totalNoContainers * settings.containerSize - colorUnits.size
        if (emptyUnitsCount > 0) {
            colorUnits.addAll(Array<Int>(emptyUnitsCount) { -1 })
        }
//        Log.d("generateGame", "ColorUnits size: ${colorUnits.size}")
//        Log.d("generateGame", "Empty units: $emptyUnitsCount")
//        Log.d("generateGame", "ColorUnits size: ${colorUnits.size}")
        colorUnits.shuffle()
        var containers: MutableList<BrickContainer> = mutableListOf()
        var brickCounter = 0
        for (i in 0..totalNoContainers - 1) {
            var startIndex = i * settings.containerSize
            if (startIndex < 0) {
                startIndex = 0
            }
            var endIndex = i * settings.containerSize + settings.containerSize
            Log.d("generateGame", "startIndex: $startIndex, endIndex: $endIndex")
            var units = colorUnits.slice(IntRange(startIndex, endInclusive = endIndex - 1))
            var colored = units.filter { color -> color >= 0 }
            brickCounter += colored.size
            containers.add(BrickContainer(id = i + 1, initialBricks = colored, selected = false))
        }
        containers.add(
            BrickContainer(
                id = totalNoContainers + 1,
                initialBricks = emptyList(),
                selected = false
            )
        )
        containers.add(
            BrickContainer(
                id = totalNoContainers + 2,
                initialBricks = emptyList(),
                selected = false
            )
        )
        return containers
    }
}