package com.flexi.colorsort.models

import androidx.compose.ui.graphics.Color
import com.flexi.colorsort.ui.theme.BrickColorScheme

data class GameSettings(val containerCount:Int = 10,
                        val containerSize:Int = 10,
                        val colorCount:Int = 10,
                        val colorSchemeId:Int = 0)

class ColorSchemes {
    companion object{
        private val colorSchemes: List<BrickColorScheme> = listOf(
            BrickColorScheme(
                Color.Yellow,
                Color.Blue,
                Color.Green,
                Color.Magenta,
                Color(157, 0, 255), //purple
                Color.Red, // light yellow
                Color.Cyan,
                Color(133, 255, 133),
                Color(196, 107, 255, 255), // purple
                Color(255, 170, 0) //orange
            ),
            BrickColorScheme(
                Color(255,255,255),
                Color(230, 230, 230),
                Color(205, 205, 205),
                Color(180, 180, 180),
                Color(155, 155, 155),
                Color(130, 130, 130),
                Color(105, 105, 105),
                Color(80, 80, 80),
                Color(55, 55, 55),
                Color(30, 30, 30)
            )
        )
        fun getColorSchemeIds():List<Int>{
            return List<Int>(colorSchemes.size){ i -> i}
        }
        fun getColorScheme(id:Int): BrickColorScheme{
            return colorSchemes.elementAt(id)
        }
    }
}