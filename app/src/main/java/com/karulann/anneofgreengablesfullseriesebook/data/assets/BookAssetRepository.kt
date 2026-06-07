package com.karulann.anneofgreengablesfullseriesebook.data.assets

import android.content.Context
import com.karulann.anneofgreengablesfullseriesebook.domain.model.Book
import com.karulann.anneofgreengablesfullseriesebook.domain.model.BookContent
import com.karulann.anneofgreengablesfullseriesebook.domain.model.Chapter
import org.json.JSONObject

class BookAssetRepository(
    private val context: Context
) {
    fun loadBookCatalog(): List<Book> {
        return SampleBooks.books.map { sampleBook ->
            val bookContent = loadBookContent(sampleBook.id)

            if (bookContent != null) {
                sampleBook.copy(
                    title = bookContent.title,
                    author = bookContent.author,
                    year = bookContent.year,
                    chapterCount = bookContent.chapters.size
                )
            } else {
                sampleBook
            }
        }
    }

    fun loadBookContent(bookId: String): BookContent? {
        return runCatching {
            val fileName = when (bookId) {
                "anne_of_green_gables" -> "books/anne_of_green_gables.json"
                else -> return null
            }

            val jsonText = context.assets.open(fileName)
                .bufferedReader()
                .use { reader ->
                    reader.readText()
                }

            val root = JSONObject(jsonText)
            val chaptersJson = root.getJSONArray("chapters")

            val chapters = buildList {
                for (index in 0 until chaptersJson.length()) {
                    val chapterJson = chaptersJson.getJSONObject(index)

                    add(
                        Chapter(
                            number = chapterJson.getInt("number"),
                            title = chapterJson.getString("title"),
                            content = chapterJson.getString("content")
                        )
                    )
                }
            }

            BookContent(
                id = root.getString("id"),
                title = root.getString("title"),
                author = root.getString("author"),
                year = root.getInt("year"),
                source = root.getString("source"),
                chapters = chapters
            )
        }.getOrNull()
    }
}