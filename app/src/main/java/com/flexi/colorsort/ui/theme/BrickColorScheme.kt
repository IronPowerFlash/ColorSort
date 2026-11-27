package com.flexi.colorsort.ui.theme

import androidx.compose.ui.graphics.Color

class BrickColorScheme(
    val id:Int,
    val name:String,
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
    val color11:Color,
    val color12: Color,
    val color13: Color,
    val color14: Color
){
    val colorMap = mapOf(
        Pair<Int, Color>(0, color1),
        Pair<Int, Color>(1, color2),
        Pair<Int, Color>(2, color3),
        Pair<Int, Color>(3, color4),
        Pair<Int, Color>(4, color5),
        Pair<Int, Color>(5, color6),
        Pair<Int, Color>(6, color7),
        Pair<Int, Color>(7, color8),
        Pair<Int, Color>(8, color9),
        Pair<Int, Color>(9, color10),
        Pair<Int, Color>(10, color11),
        Pair<Int, Color>(11, color12),
        Pair<Int, Color>(12, color13),
        Pair<Int, Color>(13, color14)
    )
}