package dev.matko.tictactoe

import dev.matko.tictactoe.exceptions.CannotPlayAfterFinishedGameException
import dev.matko.tictactoe.exceptions.FieldAlreadyPlayedException
import dev.matko.tictactoe.exceptions.PlayedTwiceException

private const val INITIAL_BOARD = "........."

class Game(val victoryListener: VictoryListener? = null) {

    interface VictoryListener {
        fun onVictory(sign: Sign)
    }

    private var board: String = INITIAL_BOARD
    private var turn = Sign.X
    private var winner: Sign? = null

    fun playX(row: Int, column: Int) {
        playSign(Sign.X, row, column)
    }

    fun playO(row: Int, column: Int) {
        playSign(Sign.O, row, column)
    }

    fun logBoard(): String {
        return board.run {
            substring(0..2) + "\n" + substring(3..5) + "\n" + substring(6..8)
        }
    }

    fun reset() {
        board = INITIAL_BOARD
    }

    private fun playSign(sign: Sign, row: Int, column: Int) {

        if (row < 1 || column < 1 || row > 3 || column > 3) {
            throw NonexistentFieldException(row, column)
        }

        if (turn != sign) {
            throw PlayedTwiceException()
        }

        if (getSign(row, column) != ".") {
            throw FieldAlreadyPlayedException(row, column, sign)
        }

        winner?.let { winner ->
            throw CannotPlayAfterFinishedGameException(winner)
        }

        val index = getBoardCharacterIndex(row, column)
        board = board.replaceRange(index..index, sign.name)

        if (hasWon(sign)) {
            this.winner = sign
            victoryListener?.onVictory(sign)
        }

        turn = if (sign == Sign.X) Sign.O else Sign.X
    }

    private fun hasWon(sign: Sign): Boolean {
        val signAsString = sign.name

        return arrayOf(
            getRow(1),
            getRow(2),
            getRow(3),
            getColumn(1),
            getColumn(2),
            getColumn(3),
            getLeftToRightDiagonal(),
            getRightToLeftDiagonal(),
        ).any { it == signAsString.repeat(3) }
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

    private fun getSign(row: Int, column: Int) = board[getBoardCharacterIndex(row, column)].toString()

    private fun getLeftToRightDiagonal() = board[0].toString() + board[4] + board[8]

    private fun getRightToLeftDiagonal() = board[2].toString() + board[4] + board[6]

    private fun getBoardCharacterIndex(row: Int, column: Int) = (row - 1) * 3 + (column - 1)
}
