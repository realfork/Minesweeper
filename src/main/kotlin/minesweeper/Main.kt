package minesweeper

import minesweeper.logic.utils.Difficulty
import minesweeper.ui.Grid

// Settings
object Main {
    val difficulty = Difficulty.EASY
}

fun main() = Grid.generateUI()