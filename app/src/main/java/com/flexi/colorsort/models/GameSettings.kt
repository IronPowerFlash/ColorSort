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
                1, "Rainbow",
                Color(0xFFFF595E),
                Color(0xFFFF7955),
                Color(0xFFFF924C),
                Color(0xFFFFCA3A),
                Color(0xFFC5CA30),
                Color(0xFF8AC926),
                Color(0xFF52A675),
                Color(0xFF35A0A0),
                Color(0xFF1982C4),
                Color(0xFF4267AC),
                Color(0xFF6A4C93),
                Color(0xFF8870AE),
                Color(0xFFB5A6C9),
                Color(0xFFD0A0B8)
            ),
            BrickColorScheme(
                2, "Caramel",
                Color(0xFFFFF1D6),
                Color(0xFFF7D9AE),
                Color(0xFFEFC18C),
                Color(0xFFE9A568),
                Color(0xFFDF8848),
                Color(0xFFCB7137),
                Color(0xFFB85B32),
                Color(0xFFA34C2C),
                Color(0xFF8C422A),
                Color(0xFF753923),
                Color(0xFF633324),
                Color(0xFF512D25),
                Color(0xFF3D2925),
                Color(0xFF292321)

            ),
            BrickColorScheme(
                3, "Sunset",
                Color(0xFF003F5C),
                Color(0xFF2C4875),
                Color(0xFF58508D),
                Color(0xFF8A508F),
                Color(0xFFBC5090),
                Color(0xFFDE5A79),
                Color(0xFFFF6361),
                Color(0xFFFF8531),
                Color(0xFFFFAC59),
                Color(0xFFFFD380),
                Color(0xFFFFE0A3),
                Color(0xFFE9A58A),
                Color(0xFFD68B9B),
                Color(0xFFA978A5)
            ),
            BrickColorScheme(
                4, "Pastel",
                Color(0xFFFFADAD),
                Color(0xFFFFBFA5),
                Color(0xFFFFD6A5),
                Color(0xFFFDFFB6),
                Color(0xFFE2FFC2),
                Color(0xFFCAFFBF),
                Color(0xFF96E8FF),
                Color(0xFFA0C4FF),
                Color(0xFFBDB2FF),
                Color(0xFFCDB5FF),
                Color(0xFFDEBCFF),
                Color(0xFFFFC6FF),
                Color(0xFFFFA7DC),
                Color(0xFFFFADD0)
            ),
            BrickColorScheme(
                5, "Retro",
                Color(0xFF20DF40),
                Color(0xFF00A036),
                Color(0xFF8FEF9F),
                Color(0xFF4D85FF),
                Color(0xFF0026FF),
                Color(0xFF3355FF),
                Color(0xFF7420DF),
                Color(0xFF47009E),
                Color(0xFF990000),
                Color(0xFFE01F1F),
                Color(0xFFFF6333),
                Color(0xFFDF8A20),
                Color(0xFFBFA020),
                Color(0xFFD45B78)
            ),
            BrickColorScheme(
                6, "Neon",
                Color(0xFFFF0000),
                Color(0xFFFF0088),
                Color(0xFFFF00FF),
                Color(0xFFA000FF),
                Color(0xFF681FFF),
                Color(0xFF0059DA),
                Color(0xFF008BFF),
                Color(0xFF00FFFF),
                Color(0xFF00FF88),
                Color(0xFF00FF00),
                Color(0xFFAAFF00),
                Color(0xFFFFFF00),
                Color(0xFFFFAA00),
                Color(0xFFFF6600)
            ),
            BrickColorScheme(
                7, "Vintage",
                Color(0xFF90EA99),
                Color(0xFF5EBB86),
                Color(0xFF2C8B72),
                Color(0xFF275E5D),
                Color(0xFF223147),
                Color(0xFF39295D),
                Color(0xFF4A297F),
                Color(0xFF5B28A0),
                Color(0xFF80508D),
                Color(0xFFA45B78),
                Color(0xFFD7423B),
                Color(0xFFE87532),
                Color(0xFFFDA909),
                Color(0xFFF5C45B)
            ),

            BrickColorScheme(
                8, "Ocean",
                Color(0xFF2AFF9E),
                Color(0xFF2EEAA9),
                Color(0xFF31D6B3),
                Color(0xFF35C1BE),
                Color(0xFF39ACC9),
                Color(0xFF378FD5),
                Color(0xFF3679DB),
                Color(0xFF3568E1),
                Color(0xFF485AF3),
                Color(0xFF4C45FE),
                Color(0xFF4935E0),
                Color(0xFF642FCB),
                Color(0xFF7836B8),
                Color(0xFF8A3BA8)
            ),
            BrickColorScheme(
                9, "Depression",
                Color(0xFF000000),
                Color(0xFF171717),
                Color(0xFF292929),
                Color(0xFF383838),
                Color(0xFF484848),
                Color(0xFF595959),
                Color(0xFF696969),
                Color(0xFF7A7A7A),
                Color(0xFF8B8B8B),
                Color(0xFF9C9C9C),
                Color(0xFFADADAD),
                Color(0xFFBEBEBE),
                Color(0xFFD5D5D5),
                Color(0xFFFFFFFF)
            ),
            BrickColorScheme(
                10, "Aurora",
                Color(0xFF253C78),
                Color(0xFF3659A8),
                Color(0xFF438BCB),
                Color(0xFF54C7C1),
                Color(0xFF65E6A5),
                Color(0xFFA2ED76),
                Color(0xFFE4E879),
                Color(0xFFF5C76B),
                Color(0xFFF39A75),
                Color(0xFFE87891),
                Color(0xFFC66EBD),
                Color(0xFF9869CA),
                Color(0xFF7057AE),
                Color(0xFF453C83)
            ),
            BrickColorScheme(
                10, "Cotton Candy",
                Color(0xFFF5FBFF),
                Color(0xFFDCEEFF),
                Color(0xFFB9DCFF),
                Color(0xFF91C8F5),
                Color(0xFF65ADEB),
                Color(0xFF438FD8),
                Color(0xFF4778B8),
                Color(0xFF8E9FD9),
                Color(0xFFB7B9EC),
                Color(0xFFD5C9F5),
                Color(0xFFF5C9E0),
                Color(0xFFF5A9CD),
                Color(0xFFEC80B5),
                Color(0xFFD95791)
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
            IconData(R.drawable.alien, "Alien"),
            IconData(R.drawable.atom, "Atom"),
            IconData(R.drawable.atom_2, "Atom 2"),
            IconData(R.drawable.bell, "Bell"),
            IconData(R.drawable.bomb, "Bomb"),
            IconData(R.drawable.bowling, "Bowling"),
            IconData(R.drawable.bug_spider, "Spider"),
            IconData(R.drawable.candy, "Candy"),
            IconData(R.drawable.car, "Car"),
            IconData(R.drawable.cat, "Cat"),
            IconData(R.drawable.cat_triangle, "Cat"),
            IconData(R.drawable.center_square, "Center square"),
            IconData(R.drawable.center_wheel, "Center wheel"),
            IconData(R.drawable.circular_connectivity, "Connected dots"),
            IconData(R.drawable.clouds, "Cloud"),
            IconData(R.drawable.coffee, "Coffee"),
            IconData(R.drawable.connection, "Link"),
            IconData(R.drawable.connection_pattern),
            IconData(R.drawable.contrast, "Half circle"),
            IconData(R.drawable.control, "Control"),
            IconData(R.drawable.cookie, "Cookie 2"),
            IconData(R.drawable.cookie_2, "Cookie"),
            IconData(R.drawable.dead_star, "Dead star"),
            IconData(R.drawable.diamond, "Rhombus"),
            IconData(R.drawable.diamond_1, "Five-point"),
            IconData(R.drawable.diamond_2, "Diamond"),
            IconData(R.drawable.diamond_half, "Half diamond"),
            IconData(R.drawable.dog, "Dog"),
            IconData(R.drawable.dribbble, "Basketball"),
            IconData(R.drawable.drink, "Drink"),
            IconData(R.drawable.drop, "Drop"),
            IconData(R.drawable.drop_2, "Drop 2"),
            IconData(R.drawable.egg, "Egg"),
            IconData(R.drawable.emoji_happy, "Laugh"),
            IconData(R.drawable.emoji_happy_2, "Happy"),
            IconData(R.drawable.emoji_happy_dead_circle, "Dead happy"),
            IconData(R.drawable.emoji_happy_square_round, "Happy square"),
            IconData(R.drawable.emoji_minecraft, "Minecraft"),
            IconData(R.drawable.emoji_neutral, "Hmpf"),
            IconData(R.drawable.emoji_neutral_simple, "Dead"),
            IconData(R.drawable.emoji_simple_no_face_circle, "Eyes only"),
            IconData(R.drawable.emoji_surprised_circle, "Surprised"),
            IconData(R.drawable.emoji_tongue_sticking_out, "Tongue"),
            IconData(R.drawable.eyes, "Eyes"),
            IconData(R.drawable.flash, "Flash"),
            IconData(R.drawable.flower, "Tulip"),
            IconData(R.drawable.flower_105, "Flower"),
            IconData(R.drawable.game_controller, "Game Controller"),
            IconData(R.drawable.game_controller_2, "Game Pad"),
            IconData(R.drawable.game_pad, "Game Pad 2"),
            IconData(R.drawable.game_of_thrones),
            IconData(R.drawable.ghost, "Ghost"),
            IconData(R.drawable.ghost_2, "Ghost 2"),
            IconData(R.drawable.headphones, "Headphones"),
            IconData(R.drawable.heart, "Heart"),
            IconData(R.drawable.horse, "Horse"),
            IconData(R.drawable.important_details),
            IconData(R.drawable.key, "Key"),
            IconData(R.drawable.lightning, "Lightning"),
            IconData(R.drawable.mill, "Mill"),
            IconData(R.drawable.moon, "Moon"),
            IconData(R.drawable.mr_robot, "Fawkes"),
            IconData(R.drawable.moustache, "Moustache"),
            IconData(R.drawable.music, "Music"),
            IconData(R.drawable.pac_man, "Pac man"),
            IconData(R.drawable.planet, "Planet"),
            IconData(R.drawable.planet_system, "Solar System"),
            IconData(R.drawable.pyramid, "Pyramid"),
            IconData(R.drawable.record, "Dot"),
            IconData(R.drawable.settings, "Cog"),
            IconData(R.drawable.shaded, "Shaded box"),
            IconData(R.drawable.shaded_2, "Shaded circle"),
            IconData(R.drawable.ship, "Ship"),
            IconData(R.drawable.shut_down, "Off"),
            IconData(R.drawable.skull, "Skull"),
            IconData(R.drawable.skull_bandaid, "Skull with band-aid"),
            IconData(R.drawable.spiral, "Spiral"),
            IconData(R.drawable.spiral_square, "Square spiral"),
            IconData(R.drawable.star, "Star"),
            IconData(R.drawable.star_3),
            IconData(R.drawable.stats_1),
            IconData(R.drawable.sword, "Sword"),
            IconData(R.drawable.target_1, "Bullseye"),
            IconData(R.drawable.target_2, "Dart"),
            IconData(R.drawable.tennis_ball, "Tennis"),
            IconData(R.drawable.tie, "Tie"),
            IconData(R.drawable.tinder, "Fire"),
            IconData(R.drawable.tree, "Tree"),
            IconData(R.drawable.ufo, "Ufo"),
            IconData(R.drawable.umbrella, "Umbrella"),
            IconData(R.drawable.weighted_storage_cube),
            IconData(R.drawable.settings_wrench, "Wrench"),
            IconData(R.drawable.wheel, "Wheel")
        )

        val iconMap = availableIcons.mapIndexed { i, icon -> i+1 to icon.iconResourceId }.toMap()

        fun getRandomIconIds(count:Int, excludeIconIds: List<Int> = emptyList<Int>()):List<Int>{
            return iconMap.filter { !excludeIconIds.contains(it.key) }
                .map{ it.key}
                .toList<Int>()
                .shuffled()
                .take(count)
        }
    }
}

data class IconData(val iconResourceId: Int, val description:String = "")