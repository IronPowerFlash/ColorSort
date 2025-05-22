package com.flexi.colorsort.models

import androidx.compose.ui.graphics.Color
import com.flexi.colorsort.ui.theme.Blue2
import com.flexi.colorsort.ui.theme.Blue3
import com.flexi.colorsort.ui.theme.Blue4
import com.flexi.colorsort.ui.theme.BrickColorScheme
import com.flexi.colorsort.ui.theme.Green2
import com.flexi.colorsort.ui.theme.Green3
import com.flexi.colorsort.ui.theme.Green4
import com.flexi.colorsort.ui.theme.Purple2
import com.flexi.colorsort.ui.theme.Purple3
import com.flexi.colorsort.ui.theme.Red2
import com.flexi.colorsort.ui.theme.Red3

data class GameSettings(
    val containerCount: Int = 10,
    val containerSize: Int = 4,
    val colorCount: Int = 8,
    val colorSchemeId: Int = 1,
)

class ColorSchemes {
    companion object {
        private val colorSchemes: List<BrickColorScheme> = listOf(
            BrickColorScheme(
                1, "Candy",
                Color(0xFFFA0000),
                Color(0xFFEDA20E),
                Color(0xFF9EDf1C),
                Color(0xFF2AD22A),
                Color(0xFF38C596),
                Color(0xFF4691B7),
                Color(0xFF5555A9),
                Color(0xFF89639C),
                Color(0xFF8E7284),
                Color(0xFF808080)
            ),
            BrickColorScheme(
                2, "Sunset",
                Color(0xFF003f5c),
                Color(0xFF2c4875),
                Color(0xFF58508d),
                Color(0xFF8a508f),
                Color(0xFFbc5090),
                Color(0xFFde5a79),
                Color(0xFFff6361),
                Color(0xFFff8531),
                Color(0xFFffac59),
                Color(0xFFffd380)
            ),
            BrickColorScheme(
                3, "Pastel",
                Color(0xFFffadad),
                Color(0xFFffd6a5),
                Color(0xFFfdffb6),
                Color(0xFFcaffbf),
                Color(0xFF96e8ff),
                Color(0xFFa0c4ff),
                Color(0xFFbdb2ff),
                Color(0xFFdebcff),
                Color(0xFFffc6ff),
                Color(0xFFffa7dc)
            ),
            BrickColorScheme(
                4, "Rainbow",
                Color(0xFFff595e),
                Color(0xFFff924c),
                Color(0xFFffca3a),
                Color(0xFFc5ca30),
                Color(0xFF8ac926),
                Color(0xFF52a675),
                Color(0xFF1982c4),
                Color(0xFF4267ac),
                Color(0xFF6a4c93),
                Color(0xFFb5a6c9)
            ),
            BrickColorScheme(
                5, "Retro",
                Green3,
                Blue3,
                Purple3,
                Red3,
                Green2,
                Blue2,
                Purple2,
                Red2,
                Green4,
                Blue4
            ),
            BrickColorScheme(
                6, "Neon",
                Color(0xFFFFFF00),
                Color(0xFF00FF00),
                Color(0xFFFF00FF),
                Color(0xFF00FFFF),
                Color(255, 170, 0),
                Color(0xFFFF0000),
                Color(146, 220, 67, 255),
                Color(196, 107, 255, 255),
                Color(0xFF0059DA),
                Color(104, 31, 255, 255)
            ),
            BrickColorScheme(
                7, "Vintage",
                Color(0xFF90ea99),
                Color(0xFF5ebb86),
                Color(0xFF2c8b72),
                Color(0xFF275e5d),
                Color(0xFF223147),
                Color(0xFF39295d),
                Color(0xFF4a297f),
                Color(0xFF5b28a0),
                Color(0xFFd7423b),
                Color(0xFFfda909)
            ),

            BrickColorScheme(
                8, "Ocean",
                Color(0xFF2aff9e),
                Color(0xFF2eeaa9),
                Color(0xFF31d6b3),
                Color(0xFF35c1be),
                Color(0xFF39acc9),
                Color(0xFF3d98d3),
                Color(0xFF4083de),
                Color(0xFF446ee9),
                Color(0xFF485af3),
                Color(0xFF4c45fe)
            ),
            BrickColorScheme(
                9, "Depression",
                Color(255, 255, 255),
                Color(225, 225, 225),
                Color(175, 175, 175),
                Color(150, 150, 150),
                Color(125, 125, 125),
                Color(100, 100, 100),
                Color(75, 75, 75),
                Color(50, 50, 50),
                Color(25, 25, 25),
                Color(0, 0, 0)
            )
        )

        fun getColorSchemeIds(): List<Int> {
            return colorSchemes.map { it.id }
        }

        fun getColorScheme(id: Int): BrickColorScheme {
            return colorSchemes.first { it.id == id }
        }
    }
}