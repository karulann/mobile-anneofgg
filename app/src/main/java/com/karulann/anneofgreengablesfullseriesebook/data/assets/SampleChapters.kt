package com.karulann.anneofgreengablesfullseriesebook.data.assets

import com.karulann.anneofgreengablesfullseriesebook.domain.model.Chapter

object SampleChapters {

    fun getChaptersForBook(bookId: String): List<Chapter> {
        return listOf(
            Chapter(
                number = 1,
                title = "Chapter 1",
                content = """
                    This is temporary reader text for the first chapter.

                    Later, this screen will load the actual chapter content from Project Gutenberg text files.

                    The purpose of this temporary text is to test the reading layout, font size, spacing, and chapter navigation.
                """.trimIndent()
            ),
            Chapter(
                number = 2,
                title = "Chapter 2",
                content = """
                    This is temporary reader text for the second chapter.

                    The final app will store all books offline so users can read without internet access.

                    We will also make this screen more comfortable for children, adults, and elderly readers.
                """.trimIndent()
            ),
            Chapter(
                number = 3,
                title = "Chapter 3",
                content = """
                    This is temporary reader text for the third chapter.

                    In the next development phases, we will add saved reading progress, bookmarks, and reader preferences.

                    For now, we only need a clean working reader foundation.
                """.trimIndent()
            )
        )
    }
}