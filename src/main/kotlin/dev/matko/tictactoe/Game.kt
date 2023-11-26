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

        checkIfWon(Sign.X)

        turn = Sign.O
    }

    private fun checkIfWon(sign: Sign) {
        val signAsString = if (sign == Sign.X) "X" else "O"

        val didSignWinWin = arrayOf(
            getRow(1),
            getRow(2),
            getRow(3),
            getColumn(1),
            getColumn(2),
            getColumn(3),
            getLeftToRightDiagonal(),
            getRightToLeftDiagonal(),
        ).any { it == signAsString.repeat(3) }

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

    private fun getColumn(column: Int): String {
        return when (column) {
            1 -> board[0].toString() + board[3] + board[6]
            2 -> board[1].toString() + board[4] + board[7]
            else -> board[2].toString() + board[5] + board[8]
        }
    }

    private fun getLeftToRightDiagonal(): String {
        return board[0].toString() + board[4] + board[8]
    }

    private fun getRightToLeftDiagonal(): String {
        return board[2].toString() + board[4] + board[6]
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
