package com.flexi.colorsort.ui.theme

import androidx.compose.ui.graphics.Color

class BrickColorScheme(
    val color1:Color,
    val color2: Color,
    val color3:Color,
    val color4:Color,
    val color5:Color,
    val color6:Color,
    val color7: Color,
    val color8:Color,
    val color9: Color,
    val color10:Color,

){
    val colorMap = mapOf(Pair<Int, Color>(1, color1),
        Pair<Int, Color>(2, color2),
        Pair<Int, Color>(3, color3),
        Pair<Int, Color>(4, color4),
        Pair<Int, Color>(5, color5),
        Pair<Int, Color>(6, color6),
        Pair<Int, Color>(7, color7),
        Pair<Int, Color>(8, color8),
        Pair<Int, Color>(9, color9),
        Pair<Int, Color>(10, color10))
}