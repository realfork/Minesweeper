package minesweeper.logic

import java.awt.Color

object Utils {
    fun numberToColor(number: Int): Color {
        return when (number) {
            1 -> Color.BLUE
            2 -> Color.GREEN
            3 -> Color.RED
            4 -> Color.MAGENTA
            5 -> Color.YELLOW
            6 -> Color.CYAN
            7 -> Color.DARK_GRAY
            8 -> Color.PINK
            else -> Color.BLACK
        }
    }
}