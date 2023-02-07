package minesweeper.logic.settings

import minesweeper.logic.utils.Difficulty
import java.util.*
import javax.swing.ImageIcon

object Constants {
    // Config
    val difficulty = Difficulty.EASY

    // Helpers
    val timer = Timer()

    // Icons
    val flagIcon = ImageIcon(javaClass.getResource("/flag.png"))
    val timeIcon = ImageIcon(javaClass.getResource("/clock.png"))
}