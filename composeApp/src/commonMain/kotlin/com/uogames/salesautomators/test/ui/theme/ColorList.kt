package com.uogames.salesautomators.test.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


object ColorList {

    val COLORS
        @Composable get() = MaterialTheme.colors.copy(
            background = Color.White,

            primary = Color(0xFF800000),
            primaryVariant = Color(0xCC0E0E0E),
            onPrimary = Color.White,

            surface = Color(0xFFDCDCDC),
            onSurface = Color.Black,

            error = Color(0xFFFFA500),


        )

}



