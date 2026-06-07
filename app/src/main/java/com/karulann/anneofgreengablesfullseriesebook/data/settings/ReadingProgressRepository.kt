package com.karulann.anneofgreengablesfullseriesebook.data.settings

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.readingProgressDataStore by preferencesDataStore(
    name = "reading_progress"
)

class ReadingProgressRepository(
    private val context: Context
) {
    fun getLastChapterIndexFlow(bookId: String): Flow<Int> {
        val key = intPreferencesKey("last_chapter_index_$bookId")

        return context.readingProgressDataStore.data.map { preferences ->
            preferences[key] ?: 0
        }
    }

    suspend fun updateLastChapterIndex(
        bookId: String,
        chapterIndex: Int
    ) {
        val key = intPreferencesKey("last_chapter_index_$bookId")

        context.readingProgressDataStore.edit { preferences ->
            preferences[key] = chapterIndex.coerceAtLeast(0)
        }
    }

    suspend fun resetProgress(bookId: String) {
        val key = intPreferencesKey("last_chapter_index_$bookId")

        context.readingProgressDataStore.edit { preferences ->
            preferences.remove(key)
        }
    }
}