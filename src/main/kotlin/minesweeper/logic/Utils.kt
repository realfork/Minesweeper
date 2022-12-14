package minesweeper.logic

import java.awt.Color

object Utils {
    fun numberToColor(number: Int): Color {
        return when (number) {
            1 -> Color(28, 119, 208)
            2 -> Color(56, 142, 60)
            3 -> Color(211, 47, 47)
            4 -> Color(123, 31, 162)
            5 -> Color(255, 143, 0)
            6 -> Color.CYAN
            7 -> Color.DARK_GRAY
            8 -> Color.PINK
            else -> Color.BLACK
        }
    }
}