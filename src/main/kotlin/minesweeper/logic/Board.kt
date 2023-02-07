package minesweeper.logic

import minesweeper.logic.settings.Constants
import minesweeper.logic.utils.Utils
import minesweeper.ui.Grid
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

object Board {
    val tiles = mutableListOf<Tile>()

    var generated = false

    fun generate(startTile: Tile) {
        tiles.filter { !startTile.surrounding().contains(it) }
            .shuffled()
            .take(Utils.getNumberOfBombs())
            .forEach { it.isBomb = true }

        for (tile in tiles.iterator()) {
            if (tile.isBomb) tile.surrounding().forEach { it.increment() }
        }

        // Start game
        this.generated = true
        Constants.timer.scheduleAtFixedRate(0, 1000) {
            Grid.timer.apply {
                text = text.toInt().inc().toString().padStart(3, '0')
            }
        }
    }

    fun getBlanks(tile: Tile, blanks: MutableList<Tile> = mutableListOf()): MutableList<Tile> {
        if (!blanks.contains(tile)) tile.also { blanks.add(it) }.surrounding().apply {
            filter { !it.isBomb && !blanks.contains(it) }
            forEach {
                if (it.getNumber() == 0) getBlanks(it, blanks)
                else blanks.add(it)
            }
        }

        return blanks
    }

    init {
        val size = Utils.getBoardSize()

        for (y in 1..size.y) {
            for (x in 1..size.x) {
                tiles.add(Tile(x, y))
            }
        }
    }
}