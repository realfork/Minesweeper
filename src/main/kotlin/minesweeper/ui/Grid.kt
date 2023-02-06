package minesweeper.ui

import minesweeper.logic.Board
import minesweeper.logic.utils.Utils
import java.awt.GridLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities
import kotlin.concurrent.schedule

object Grid {
    private val frame = JFrame().apply { defaultCloseOperation = JFrame.EXIT_ON_CLOSE }
    private val panel = JPanel().apply {
        Utils.getBoardSize().let {
            layout = GridLayout(it.y, it.x)
        }
    }

    fun generateUI() {
        for (tile in Board.tiles) {
            tile.button.addMouseListener(object : MouseListener {
                override fun mouseReleased(e: MouseEvent?) {
                    // Generate bombs on first click
                    if (!Board.generated) Board.generate(tile)
                    if (tile.isRevealed()) return

                    // Flag
                    if (SwingUtilities.isRightMouseButton(e)) return tile.updateFlag()
                    if (tile.isFlagged()) return

                    // Logic
                    if (tile.isBomb) frame.dispose()
                    else if (tile.getNumber() > 0) tile.reveal()
                    else Board.getBlanks(tile).forEach { it.reveal() } // Explodes all blank tiles

                    // Check if entire grid is revealed
                    if (Board.tiles
                            .filter { !it.isBomb }
                            .all { it.isRevealed() }
                    ) {
                        println("You won!")
                        Timer().schedule(2000) {
                            frame.dispose()
                            this.cancel()
                        }
                    }
                }

                // Other interface methods
                override fun mouseClicked(e: MouseEvent?) {}
                override fun mousePressed(e: MouseEvent?) {}
                override fun mouseEntered(e: MouseEvent?) {}
                override fun mouseExited(e: MouseEvent?) {}
            })
            panel.add(tile.button)
        }

        frame.apply {
            contentPane.add(panel)
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }
    }
}