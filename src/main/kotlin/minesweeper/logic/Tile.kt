package minesweeper.logic

import java.awt.Color
import java.awt.Dimension
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JButton

class Tile(private val x: Int, private val y: Int) {
    // LOGIC
    var isBomb = false
    private var number = 0

    fun getNumber() = number
    fun increment() = number++

    private fun up(): Tile? = Board.tiles.find { it.x == this.x && it.y == this.y + 1 }
    private fun down(): Tile? = Board.tiles.find { it.x == this.x && it.y == this.y - 1 }
    private fun left(): Tile? = Board.tiles.find { it.x == this.x - 1 && it.y == this.y }
    private fun right(): Tile? = Board.tiles.find { it.x == this.x + 1 && it.y == this.y }

    fun surrounding(): List<Tile> {
        return listOfNotNull(
            up()?.left(),
            up(),
            up()?.right(),
            left(),
            right(),
            down()?.left(),
            down(),
            down()?.right()
        )
    }

    // UI
    val button = JButton("")

    // Colors
    private var grassColor = if (x % 2 == y % 2) Color(170, 215, 81) else Color(162, 209, 73)
    private var tileColor = if (x % 2 == y % 2) Color(229, 194, 159) else Color(215, 184, 153)

    // Flag logic
    private var isFlagged = false
    fun isFlagged() = isFlagged
    fun updateFlag() {
        isFlagged = !isFlagged
        button.text = if (isFlagged) "X" else ""
        button.foreground = if (!isFlagged) Utils.numberToColor(number) else Color.RED
    }

    // Reveal logic
    private var isRevealed = false
    fun isRevealed() = isRevealed
    fun reveal() {
        button.background = tileColor
        if (number > 0) {
            button.foreground = Utils.numberToColor(number)
            button.text = number.toString()
        }

        isRevealed = true
    }

    init {
        button.isBorderPainted = false
        button.background = grassColor
        button.preferredSize = Dimension(50, 50)

        // Select animation
        button.addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(evt: MouseEvent) {
                // TODO Make hover animation better
                if (!Board.generated || number > 0) button.background.let {
                     button.background = Color(it.red + 22, it.green + 10, it.blue + 45)
                }
            }

            override fun mouseExited(evt: MouseEvent) {
                button.background = if (isRevealed) tileColor else grassColor
            }
        })
    }
}