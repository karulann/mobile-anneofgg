package com.karulann.anneofgreengablesfullseriesebook.feature.reader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karulann.anneofgreengablesfullseriesebook.data.assets.SampleChapters
import com.karulann.anneofgreengablesfullseriesebook.domain.model.Book
import com.karulann.anneofgreengablesfullseriesebook.domain.model.Chapter
import com.karulann.anneofgreengablesfullseriesebook.domain.model.ReaderSettings
import com.karulann.anneofgreengablesfullseriesebook.domain.model.ReaderThemeMode

@Composable
fun ReaderScreen(
    book: Book,
    readerSettings: ReaderSettings,
    chapters: List<Chapter> = SampleChapters.getChaptersForBook(book.id),
    onBackClick: () -> Unit,
    onFontSizeChange: (Float) -> Unit,
    onLineSpacingChange: (Float) -> Unit,
    onThemeChange: (ReaderThemeMode) -> Unit
) {
    var chapterIndex by remember(book.id) { mutableStateOf(0) }

    val currentChapter = chapters.getOrNull(chapterIndex)

    val backgroundColor = when (readerSettings.themeMode) {
        ReaderThemeMode.LIGHT -> MaterialTheme.colorScheme.background
        ReaderThemeMode.SEPIA -> Color(0xFFF4ECD8)
        ReaderThemeMode.DARK -> Color(0xFF121212)
    }

    val textColor = when (readerSettings.themeMode) {
        ReaderThemeMode.LIGHT -> MaterialTheme.colorScheme.onBackground
        ReaderThemeMode.SEPIA -> Color(0xFF3B2F2F)
        ReaderThemeMode.DARK -> Color(0xFFEAEAEA)
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            OutlinedButton(
                onClick = onBackClick
            ) {
                Text(text = "Back to Book")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
                modifier = Modifier.semantics { heading() }
            )

            if (currentChapter != null) {
                Text(
                    text = currentChapter.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = textColor,
                    modifier = Modifier.padding(top = 4.dp)
                )

                ReaderControls(
                    fontSizeSp = readerSettings.fontSizeSp,
                    lineSpacingMultiplier = readerSettings.lineSpacingMultiplier,
                    readerThemeMode = readerSettings.themeMode,
                    onDecreaseFont = {
                        onFontSizeChange(readerSettings.fontSizeSp - 2f)
                    },
                    onIncreaseFont = {
                        onFontSizeChange(readerSettings.fontSizeSp + 2f)
                    },
                    onDecreaseSpacing = {
                        onLineSpacingChange(readerSettings.lineSpacingMultiplier - 0.2f)
                    },
                    onIncreaseSpacing = {
                        onLineSpacingChange(readerSettings.lineSpacingMultiplier + 0.2f)
                    },
                    onThemeChange = onThemeChange
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = currentChapter.content,
                        fontSize = readerSettings.fontSizeSp.sp,
                        lineHeight = (readerSettings.fontSizeSp * readerSettings.lineSpacingMultiplier).sp,
                        color = textColor,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            if (chapterIndex > 0) {
                                chapterIndex--
                            }
                        },
                        enabled = chapterIndex > 0,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Previous")
                    }

                    Button(
                        onClick = {
                            if (chapterIndex < chapters.lastIndex) {
                                chapterIndex++
                            }
                        },
                        enabled = chapterIndex < chapters.lastIndex,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Next")
                    }
                }
            } else {
                Text(
                    text = "No chapter content available.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun ReaderControls(
    fontSizeSp: Float,
    lineSpacingMultiplier: Float,
    readerThemeMode: ReaderThemeMode,
    onDecreaseFont: () -> Unit,
    onIncreaseFont: () -> Unit,
    onDecreaseSpacing: () -> Unit,
    onIncreaseSpacing: () -> Unit,
    onThemeChange: (ReaderThemeMode) -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(onClick = onDecreaseFont) {
                Text(text = "A-")
            }

            OutlinedButton(onClick = onIncreaseFont) {
                Text(text = "A+")
            }

            Text(
                text = "Font: ${fontSizeSp.toInt()}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(onClick = onDecreaseSpacing) {
                Text(text = "Spacing-")
            }

            OutlinedButton(onClick = onIncreaseSpacing) {
                Text(text = "Spacing+")
            }

            Text(
                text = "Line: ${String.format("%.1f", lineSpacingMultiplier)}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 12.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = readerThemeMode == ReaderThemeMode.LIGHT,
                onClick = { onThemeChange(ReaderThemeMode.LIGHT) },
                label = { Text("Light") }
            )

            FilterChip(
                selected = readerThemeMode == ReaderThemeMode.SEPIA,
                onClick = { onThemeChange(ReaderThemeMode.SEPIA) },
                label = { Text("Sepia") }
            )

            FilterChip(
                selected = readerThemeMode == ReaderThemeMode.DARK,
                onClick = { onThemeChange(ReaderThemeMode.DARK) },
                label = { Text("Dark") }
            )
        }
    }
}