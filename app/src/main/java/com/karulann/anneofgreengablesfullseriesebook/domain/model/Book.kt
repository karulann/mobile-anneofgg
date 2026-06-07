package com.karulann.anneofgreengablesfullseriesebook.domain.model

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val year: Int,
    val description: String,
    val chapterCount: Int
)