package com.karulann.anneofgreengablesfullseriesebook.domain.model

data class BookContent(
    val id: String,
    val title: String,
    val author: String,
    val year: Int,
    val source: String,
    val chapters: List<Chapter>
)