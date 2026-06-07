package com.karulann.anneofgreengablesfullseriesebook.domain.model

data class ReaderSettings(
    val fontSizeSp: Float = 18f,
    val lineSpacingMultiplier: Float = 1.6f,
    val themeMode: ReaderThemeMode = ReaderThemeMode.LIGHT
)