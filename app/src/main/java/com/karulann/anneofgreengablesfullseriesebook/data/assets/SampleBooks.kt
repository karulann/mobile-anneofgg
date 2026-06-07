package com.karulann.anneofgreengablesfullseriesebook.data.assets

import com.karulann.anneofgreengablesfullseriesebook.domain.model.Book

object SampleBooks {

    val books = listOf(
        Book(
            id = "anne_of_green_gables",
            title = "Anne of Green Gables",
            author = "L. M. Montgomery",
            year = 1908,
            description = "The first story of Anne Shirley, an imaginative orphan girl who arrives at Green Gables.",
            chapterCount = 38
        ),
        Book(
            id = "anne_of_avonlea",
            title = "Anne of Avonlea",
            author = "L. M. Montgomery",
            year = 1909,
            description = "Anne begins teaching and continues her life in Avonlea.",
            chapterCount = 30
        ),
        Book(
            id = "anne_of_the_island",
            title = "Anne of the Island",
            author = "L. M. Montgomery",
            year = 1915,
            description = "Anne leaves Avonlea for college and new experiences.",
            chapterCount = 41
        ),
        Book(
            id = "annes_house_of_dreams",
            title = "Anne's House of Dreams",
            author = "L. M. Montgomery",
            year = 1917,
            description = "Anne begins married life in her new home by the sea.",
            chapterCount = 40
        )
    )
}