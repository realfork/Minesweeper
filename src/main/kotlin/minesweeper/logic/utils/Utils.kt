package minesweeper.logic.utils

import minesweeper.Main
import java.awt.Color
import java.awt.Dimension
import java.awt.Point

object Utils {
    fun getBoardSize(): Point {
        return when (Main.difficulty) {
            Difficulty.EASY -> Point(10, 8)
            Difficulty.MEDIUM -> Point(18, 14)
            Difficulty.HARD -> Point(24, 20)
        }
    }

    fun getTileDimension(): Dimension {
        return when (Main.difficulty) {
            Difficulty.EASY -> Dimension(50, 50)
            Difficulty.MEDIUM -> Dimension(40, 40) // Adjust
            Difficulty.HARD -> Dimension(30, 30) // Adjust
        }
    }

    fun getNumberOfBombs(): Int {
        return when (Main.difficulty) {
            Difficulty.EASY -> 10
            Difficulty.MEDIUM -> 40
            Difficulty.HARD -> 99
        }
    }

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