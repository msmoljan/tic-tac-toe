package dev.matko.tictactoe

import dev.matko.tictactoe.exceptions.PlayedTwiceException

class Game(val victoryListener: VictoryListener? = null) {

    interface VictoryListener {
        fun onVictory(sign: Sign)
    }

    private var board: String = "........."
    private var turn = Sign.X

    fun playX(row: Int, column: Int) {

        if (turn == Sign.O) {
            throw PlayedTwiceException()
        }

        val index = (row - 1) * 3 + (column - 1)
        board = board.replaceRange(index..index, "X")

        if (board.substring(0..2) == "XXX") {
            victoryListener?.onVictory(Sign.X)
        }

        turn = Sign.O
    }

    fun logBoard(): String {
        return board.run {
            substring(0..2) + "\n" + substring(3..5) + "\n" + substring(6..8)
        }
    }

    fun playO(row: Int, column: Int) {
        val index = (row - 1) * 3 + (column - 1)
        board = board.replaceRange(index..index, "0")

        this.turn = Sign.X
    }
}
