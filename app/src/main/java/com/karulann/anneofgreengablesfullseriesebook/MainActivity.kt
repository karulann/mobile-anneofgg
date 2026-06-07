package com.karulann.anneofgreengablesfullseriesebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.karulann.anneofgreengablesfullseriesebook.ui.theme.AnneOfGreenGablesFullSeriesEbookTheme
import com.karulann.anneofgreengablesfullseriesebook.feature.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnneOfGreenGablesFullSeriesEbookTheme {
                HomeScreen()
            }
        }
    }
}