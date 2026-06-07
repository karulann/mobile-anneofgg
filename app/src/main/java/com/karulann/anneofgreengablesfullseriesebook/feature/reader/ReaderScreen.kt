package com.karulann.anneofgreengablesfullseriesebook.feature.reader

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
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.karulann.anneofgreengablesfullseriesebook.data.assets.SampleChapters
import com.karulann.anneofgreengablesfullseriesebook.domain.model.Book
import com.karulann.anneofgreengablesfullseriesebook.domain.model.Chapter

@Composable
fun ReaderScreen(
    book: Book,
    chapters: List<Chapter> = SampleChapters.getChaptersForBook(book.id),
    onBackClick: () -> Unit
) {
    var chapterIndex by remember(book.id) { mutableStateOf(0) }
    var fontSizeSp by remember { mutableStateOf(18f) }

    val currentChapter = chapters.getOrNull(chapterIndex)

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                modifier = Modifier.semantics { heading() }
            )

            if (currentChapter != null) {
                Text(
                    text = currentChapter.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            if (fontSizeSp > 14f) {
                                fontSizeSp -= 2f
                            }
                        }
                    ) {
                        Text(text = "A-")
                    }

                    OutlinedButton(
                        onClick = {
                            if (fontSizeSp < 30f) {
                                fontSizeSp += 2f
                            }
                        }
                    ) {
                        Text(text = "A+")
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(top = 16.dp)
                ) {
                    Text(
                        text = currentChapter.content,
                        fontSize = fontSizeSp.sp,
                        lineHeight = (fontSizeSp * 1.6f).sp,
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
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}