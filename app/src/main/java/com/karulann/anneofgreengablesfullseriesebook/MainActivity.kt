package com.karulann.anneofgreengablesfullseriesebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import com.karulann.anneofgreengablesfullseriesebook.data.settings.ReaderSettingsRepository
import com.karulann.anneofgreengablesfullseriesebook.domain.model.ReaderSettings
import kotlinx.coroutines.launch
import com.karulann.anneofgreengablesfullseriesebook.data.assets.SampleBooks
import com.karulann.anneofgreengablesfullseriesebook.feature.bookdetail.BookDetailScreen
import com.karulann.anneofgreengablesfullseriesebook.feature.home.HomeScreen
import com.karulann.anneofgreengablesfullseriesebook.ui.theme.AnneOfGreenGablesFullSeriesEbookTheme
import com.karulann.anneofgreengablesfullseriesebook.feature.reader.ReaderScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AnneOfGreenGablesFullSeriesEbookTheme {
                val readerSettingsRepository = remember {
                    ReaderSettingsRepository(applicationContext)
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
                        onBackClick = {
                            isReading = false
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