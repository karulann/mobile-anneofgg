package com.karulann.anneofgreengablesfullseriesebook.data.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.karulann.anneofgreengablesfullseriesebook.domain.model.ReaderSettings
import com.karulann.anneofgreengablesfullseriesebook.domain.model.ReaderThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.readerSettingsDataStore by preferencesDataStore(
    name = "reader_settings"
)

class ReaderSettingsRepository(
    private val context: Context
) {
    private object Keys {
        val FONT_SIZE = floatPreferencesKey("font_size_sp")
        val LINE_SPACING = floatPreferencesKey("line_spacing_multiplier")
        val THEME_MODE = stringPreferencesKey("theme_mode")
    }

    val readerSettingsFlow: Flow<ReaderSettings> =
        context.readerSettingsDataStore.data.map { preferences ->
            ReaderSettings(
                fontSizeSp = preferences[Keys.FONT_SIZE] ?: 18f,
                lineSpacingMultiplier = preferences[Keys.LINE_SPACING] ?: 1.6f,
                themeMode = preferences[Keys.THEME_MODE]
                    ?.let { storedTheme ->
                        runCatching {
                            ReaderThemeMode.valueOf(storedTheme)
                        }.getOrDefault(ReaderThemeMode.LIGHT)
                    }
                    ?: ReaderThemeMode.LIGHT
            )
        }

    suspend fun updateFontSize(fontSizeSp: Float) {
        context.readerSettingsDataStore.edit { preferences ->
            preferences[Keys.FONT_SIZE] = fontSizeSp.coerceIn(14f, 32f)
        }
    }

    suspend fun updateLineSpacing(lineSpacingMultiplier: Float) {
        context.readerSettingsDataStore.edit { preferences ->
            preferences[Keys.LINE_SPACING] = lineSpacingMultiplier.coerceIn(1.2f, 2.2f)
        }
    }

    suspend fun updateThemeMode(themeMode: ReaderThemeMode) {
        context.readerSettingsDataStore.edit { preferences ->
            preferences[Keys.THEME_MODE] = themeMode.name
        }
    }
}