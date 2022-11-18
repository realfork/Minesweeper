package minesweeper.logic

class Tile(val x: Int, val y: Int) {
    var isBomb = false
    private var number = 0

    fun getNumber() = number
    fun increment() = number++

    fun up(): Tile? = Board.tiles.find { it.x == this.x && it.y == this.y + 1 }
    fun down(): Tile? = Board.tiles.find { it.x == this.x && it.y == this.y - 1 }
    fun left(): Tile? = Board.tiles.find { it.x == this.x - 1 && it.y == this.y }
    fun right(): Tile? = Board.tiles.find { it.x == this.x + 1 && it.y == this.y }

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
}