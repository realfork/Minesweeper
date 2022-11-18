package minesweeper.logic

object Board {
    val tiles = generateTiles()

    fun generateNumbers() {
        tiles.shuffled().take(10).forEach { it.isBomb = true }

        for (tile in tiles.iterator()) {
            if (tile.isBomb) tile.surrounding().forEach { it.increment() }
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

    private fun generateTiles(): List<Tile> {
        val tiles = mutableListOf<Tile>()
        for (y in 1..8) {
            for (x in 1..10) {
                tiles.add(Tile(x, y))
            }
        }

        return tiles
    }
}