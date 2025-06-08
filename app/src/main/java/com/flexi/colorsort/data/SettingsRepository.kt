package com.flexi.colorsort.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.flexi.colorsort.models.GameSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val settingsStore: DataStore<UserAppSettings>
) {
    private val TAG: String = "SettingsRepo"

    val settingsFlow: Flow<UserAppSettings> = settingsStore.data
        .catch { exception ->
            if(exception is IOException){
                Log.e(TAG, "Error reading user settings", exception)
                emit(UserAppSettings.getDefaultInstance())
            }
            else{
                throw exception
            }
        }

    suspend fun updateSettings(newSettings: GameSettings){
        settingsStore.updateData { settings ->
            settings.toBuilder().setContainerCount(newSettings.containerCount)
            .setColorCount(newSettings.colorCount)
            .setColorSchemeId(newSettings.colorSchemeId)
            .setColorblindMode(newSettings.colorBlindMode)
            .setContainerSize(newSettings.containerSize)
            .clearSelectedIcons()
            .addAllSelectedIcons(newSettings.selectedIconIds)
            .build()
        }
    }
}