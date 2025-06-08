package com.flexi.colorsort.models

import androidx.compose.ui.graphics.Color
import com.flexi.colorsort.R
import com.flexi.colorsort.ui.theme.BrickColorScheme

data class GameSettings(
    val containerCount: Int = 10,
    val containerSize: Int = 4,
    val colorCount: Int = 8,
    val colorSchemeId: Int = 1,
    val colorBlindMode:Boolean = true,
    val selectedIconIds: List<Int> = emptyList()
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
                Color(0xFF20DF40),
                Color(0xFF3355FF),
                Color(0xFF7420DF),
                Color(0xFFE01F1F),
                Color(0xFF00A036),
                Color(0xFF0026FF),
                Color(0xFF47009E),
                Color(0xFF990000),
                Color(0xFF8FEF9F),
                Color(0xFF4D85FF)
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

class IconStore {
    companion object
    {
        private val availableIcons: List<IconData> = listOf(
            IconData(R.drawable.airplane, "Plane"),
            IconData(R.drawable.atom, "Atom"),
            IconData(R.drawable.atom_2),
            IconData(R.drawable.bell, "Bell"),
            IconData(R.drawable.bomb, "Bomb"),
            IconData(R.drawable.bowling, "Bowling"),
            IconData(R.drawable.bug_spider, "Spider"),
            IconData(R.drawable.car, "Car"),
            IconData(R.drawable.center_square),
            IconData(R.drawable.center_wheel),
            IconData(R.drawable.circular_connectivity),
            IconData(R.drawable.clouds, "Cloud"),
            IconData(R.drawable.connection, "Link"),
            IconData(R.drawable.connection_pattern),
            IconData(R.drawable.contrast),
            IconData(R.drawable.control),
            IconData(R.drawable.dead_star),
            IconData(R.drawable.diamond),
            IconData(R.drawable.diamond_1),
            IconData(R.drawable.diamond_2, "Diamond"),
            IconData(R.drawable.diamond_half),
            IconData(R.drawable.dribbble),
            IconData(R.drawable.drink, "Drink"),
            IconData(R.drawable.drop, "Drop"),
            IconData(R.drawable.egg, "Egg"),
            IconData(R.drawable.emoji_happy),
            IconData(R.drawable.emoji_happy_2),
            IconData(R.drawable.emoji_happy_dead_circle),
            IconData(R.drawable.emoji_happy_square_round),
            IconData(R.drawable.emoji_minecraft),
            IconData(R.drawable.emoji_neutral),
            IconData(R.drawable.emoji_neutral_simple),
            IconData(R.drawable.emoji_simple_no_face_circle),
            IconData(R.drawable.emoji_surprised_circle),
            IconData(R.drawable.emoji_tongue_sticking_out, "Tongue"),
            IconData(R.drawable.eyes, "Eyes"),
            IconData(R.drawable.flower, "Tulip"),
            IconData(R.drawable.flower_105, "Flower"),
            IconData(R.drawable.game_controller, "Gamepad"),
            IconData(R.drawable.game_of_thrones),
            IconData(R.drawable.ghost, "Ghost"),
            IconData(R.drawable.ghost_2),
            IconData(R.drawable.headphones, "Headphones"),
            IconData(R.drawable.heart, "Heart"),
            IconData(R.drawable.important_details),
            IconData(R.drawable.key, "Key"),
            IconData(R.drawable.lightning, "Lightning"),
            IconData(R.drawable.mill, "Mill"),
            IconData(R.drawable.moon, "Moon"),
            IconData(R.drawable.mr_robot, "Fawkes"),
            IconData(R.drawable.music, "Music"),
            IconData(R.drawable.pac_man, "Pac man"),
            IconData(R.drawable.planet, "Planet"),
            IconData(R.drawable.planet_system, "Solar System"),
            IconData(R.drawable.pyramid, "Pyramid"),
            IconData(R.drawable.record),
            IconData(R.drawable.settings, "Cog"),
            IconData(R.drawable.shaded),
            IconData(R.drawable.shaded_2),
            IconData(R.drawable.ship, "Ship"),
            IconData(R.drawable.shut_down, "Off"),
            IconData(R.drawable.skull, "Skull"),
            IconData(R.drawable.spiral),
            IconData(R.drawable.star, "Star"),
            IconData(R.drawable.star_2),
            IconData(R.drawable.star_3),
            IconData(R.drawable.stats_1),
            IconData(R.drawable.target_1),
            IconData(R.drawable.target_2),
            IconData(R.drawable.tennis_ball, "Tennis"),
            IconData(R.drawable.tie, "Tie"),
            IconData(R.drawable.tinder, "Fire"),
            IconData(R.drawable.ufo, "Ufo"),
            IconData(R.drawable.umbrella, "Umbrella"),
            IconData(R.drawable.weighted_storage_cube),
            IconData(R.drawable.settings_wrench, "Wrench"),
            IconData(R.drawable.wheel, "Wheel")
        )

        val iconMap = availableIcons.mapIndexed { i, icon -> i+1 to icon.iconResourceId }.toMap()

        fun getRandomIconIds(count:Int):List<Int>{
            return iconMap.map{ it.key}.toList<Int>().shuffled().take(count)
        }
    }
}

data class IconData(val iconResourceId: Int, val description:String = "")