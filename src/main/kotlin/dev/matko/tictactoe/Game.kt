package dev.matko.tictactoe

import dev.matko.tictactoe.exceptions.PlayedTwiceException

class Game {

    private var board: String = "........."

    fun playX(row: Int, column: Int) {
        if (countX() - countO() >= 1) {
            throw PlayedTwiceException()
        }

        val index = (row - 1) * 3 + (column - 1)
        board = board.replaceRange(index, index, "X")
    }

    fun logBoard(): String {
        return board.run {
            substring(0..2) + "\n" + substring(3..5) + "\n" + substring(6..8)
        }
    }

    private fun countX(): Int {
        return board.count { character -> character == 'X' }
    }

    private fun countO(): Int {
        return board.count { character -> character == 'O' }
    }
}
