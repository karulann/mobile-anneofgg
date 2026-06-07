package com.karulann.anneofgreengablesfullseriesebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.karulann.anneofgreengablesfullseriesebook.data.assets.SampleBooks
import com.karulann.anneofgreengablesfullseriesebook.data.settings.ReaderSettingsRepository
import com.karulann.anneofgreengablesfullseriesebook.data.settings.ReadingProgressRepository
import com.karulann.anneofgreengablesfullseriesebook.domain.model.ReaderSettings
import com.karulann.anneofgreengablesfullseriesebook.feature.bookdetail.BookDetailScreen
import com.karulann.anneofgreengablesfullseriesebook.feature.home.HomeScreen
import com.karulann.anneofgreengablesfullseriesebook.feature.reader.ReaderScreen
import com.karulann.anneofgreengablesfullseriesebook.ui.theme.AnneOfGreenGablesFullSeriesEbookTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnneOfGreenGablesFullSeriesEbookTheme {
                val readerSettingsRepository = remember {
                    ReaderSettingsRepository(applicationContext)
                }

                val readingProgressRepository = remember {
                    ReadingProgressRepository(applicationContext)
                }

                val readerSettings by readerSettingsRepository.readerSettingsFlow.collectAsState(
                    initial = ReaderSettings()
                )

                val coroutineScope = rememberCoroutineScope()

                var selectedBookId by remember { mutableStateOf<String?>(null) }
                var isReading by remember { mutableStateOf(false) }

                val selectedBook = selectedBookId?.let { id ->
                    SampleBooks.books.firstOrNull { book ->
                        book.id == id
                    }
                }

                var lastChapterIndex by remember(selectedBook?.id) {
                    mutableStateOf(0)
                }

                LaunchedEffect(selectedBook?.id) {
                    val bookId = selectedBook?.id

                    if (bookId != null) {
                        readingProgressRepository
                            .getLastChapterIndexFlow(bookId)
                            .collect { savedIndex ->
                                lastChapterIndex = savedIndex
                            }
                    }
                }

                if (selectedBook == null) {
                    HomeScreen(
                        onBookClick = { book ->
                            selectedBookId = book.id
                            isReading = false
                        }
                    )
                } else if (isReading) {
                    ReaderScreen(
                        book = selectedBook,
                        readerSettings = readerSettings,
                        initialChapterIndex = lastChapterIndex,
                        onBackClick = {
                            isReading = false
                        },
                        onChapterChange = { chapterIndex ->
                            coroutineScope.launch {
                                readingProgressRepository.updateLastChapterIndex(
                                    bookId = selectedBook.id,
                                    chapterIndex = chapterIndex
                                )
                            }
                        },
                        onFontSizeChange = { newFontSize ->
                            coroutineScope.launch {
                                readerSettingsRepository.updateFontSize(newFontSize)
                            }
                        },
                        onLineSpacingChange = { newLineSpacing ->
                            coroutineScope.launch {
                                readerSettingsRepository.updateLineSpacing(newLineSpacing)
                            }
                        },
                        onThemeChange = { newTheme ->
                            coroutineScope.launch {
                                readerSettingsRepository.updateThemeMode(newTheme)
                            }
                        }
                    )
                } else {
                    BookDetailScreen(
                        book = selectedBook,
                        onBackClick = {
                            selectedBookId = null
                            isReading = false
                        },
                        onStartReadingClick = {
                            isReading = true
                        }
                    )
                }
            }
        }
    }
}