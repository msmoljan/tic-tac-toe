package dev.matko.tictactoe

import dev.matko.tictactoe.exceptions.PlayedTwiceException

class Game(val victoryListener: VictoryListener? = null) {

    interface VictoryListener {
        fun onVictory(sign: Sign)
    }

    private val winningCombinations = arrayOf(
        "***......",
        "...***...",
    )

    private var board: String = "........."
    private var turn = Sign.X

    fun playX(row: Int, column: Int) {

        if (turn == Sign.O) {
            throw PlayedTwiceException()
        }

        val index = (row - 1) * 3 + (column - 1)
        board = board.replaceRange(index..index, "X")

        checkIfWon(Sign.X)

        turn = Sign.O
    }

    private fun checkIfWon(sign: Sign) {
        val signAsString = if (sign == Sign.X) "X" else "O"

        val didSignWinWin = arrayOf(getRow(1), getRow(2), getRow(3))
            .any { it == signAsString.repeat(3) }

        if (didSignWinWin) {
            victoryListener?.onVictory(sign)
        }
    }

    private fun getRow(row: Int): String {
        return when (row) {
            1 -> board.substring(0..2)
            2 -> board.substring(3..5)
            else -> board.substring(6..8)
        }
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
