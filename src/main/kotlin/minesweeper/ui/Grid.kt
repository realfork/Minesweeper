package minesweeper.ui

import minesweeper.logic.Board
import minesweeper.logic.settings.Constants
import minesweeper.logic.utils.Utils
import java.awt.BorderLayout
import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.SwingUtilities
import kotlin.system.exitProcess

object Grid {
    private val frame = JFrame().apply { defaultCloseOperation = JFrame.EXIT_ON_CLOSE }
    private val panel = JPanel().apply {
        Utils.getBoardSize().let {
            layout = GridLayout(it.y, it.x)
        }
    }

    // Timer and flags
    val timer = JLabel("000", Constants.timeIcon, JLabel.CENTER).apply { foreground = Color.WHITE }
    val flags = JLabel(Utils.getNumberOfBombs().toString(), Constants.flagIcon, JLabel.CENTER).apply { foreground = Color.WHITE }

    private val statusBar = JPanel().apply {
        background = Color(74, 117, 44)
        layout = FlowLayout(FlowLayout.CENTER)

        // Add status info
        add(flags)
        add(timer)
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
                        println("You won! Finished in ${timer.text}s")
                        exitProcess(0)
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
            add(statusBar, BorderLayout.NORTH)
            add(panel, BorderLayout.SOUTH)
            pack()

            setLocationRelativeTo(null)
            isVisible = true
        }
    }
}