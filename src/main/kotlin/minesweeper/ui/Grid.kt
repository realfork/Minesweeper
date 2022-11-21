package minesweeper.ui

import minesweeper.logic.Board
import minesweeper.logic.Tile
import minesweeper.logic.Utils
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

object Grid {
    private val frame = JFrame().apply { defaultCloseOperation = JFrame.EXIT_ON_CLOSE }
    private val panel = JPanel().apply { layout = GridLayout(8, 10) }

    private val tileToButton = mutableMapOf<Tile, JButton>()

    fun generateUI() {
        for (tile in Board.tiles) {
            panel.add(JButton("").apply {
                val grassColor = if (tile.x % 2 == tile.y % 2) Color(170, 215, 81) else Color(162, 209, 73)
                background = grassColor

                preferredSize = Dimension(50, 50)

                addMouseListener(object : MouseListener {
                    override fun mouseReleased(e: MouseEvent?) {
                        // Flag
                        if (SwingUtilities.isRightMouseButton(e)) {
                            text = if (text.isEmpty()) "X" else ""
                            foreground = if (text.isEmpty()) Utils.numberToColor(tile.getNumber()) else Color.RED
                            return
                        }
                        if (text == "X") return

                        // Logic
                        if (tile.isBomb) frame.dispose()
                        else if (tile.getNumber() > 0) {
                            background = if (tile.x % 2 == tile.y % 2) Color(229, 194, 159) else Color(215, 184, 153)
                            foreground = Utils.numberToColor(tile.getNumber())

                            text = tile.getNumber().toString()
                        }

                        // Explode blank tiles
                        else Board.getBlanks(tile).forEach {
                            // Break
                            tileToButton[it]?.apply {
                                background = if (it.x % 2 == it.y % 2) Color(229, 194, 159) else Color(215, 184, 153)

                                if (it.getNumber() > 0) {
                                    foreground = Utils.numberToColor(it.getNumber())
                                    text = it.getNumber().toString()
                                }
                            }
                        }
                    }

                    // Other interface methods
                    override fun mouseClicked(e: MouseEvent?) {}
                    override fun mousePressed(e: MouseEvent?) {}
                    override fun mouseEntered(e: MouseEvent?) {}
                    override fun mouseExited(e: MouseEvent?) {}
                })
            }.also { tileToButton[tile] = it })
        }

        frame.apply {
            contentPane.add(panel)
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }
    }
}