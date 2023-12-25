package dev.matko.tictactoe.domain

import dev.matko.tictactoe.domain.exceptions.CannotPlayAfterDrawException
import dev.matko.tictactoe.domain.exceptions.CannotPlayAfterFinishedGameException
import dev.matko.tictactoe.domain.exceptions.FieldAlreadyPlayedException
import dev.matko.tictactoe.domain.exceptions.NonexistentFieldException

private const val INITIAL_BOARD = "........."

class Game(private val gameListener: GameListener? = null) {

    interface GameListener {
        fun onVictory(sign: Sign)
        fun onGameChanged()
        fun onDraw()
    }

    var currentPlayer = Sign.X
        private set

    private var board: String = INITIAL_BOARD
    private var winner: Sign? = null
    private var hasEndedInDraw = false

    var isFinished: Boolean
        get() = winner != null
        private set(_) = throw IllegalStateException("This should not be callable")

    fun play(row: Int, column: Int) {

        if (row < 1 || column < 1 || row > 3 || column > 3) {
            throw NonexistentFieldException(row, column)
        }

        if (hasEndedInDraw) throw CannotPlayAfterDrawException()

        val existingSignAtField = getSign(row, column)
        if (existingSignAtField != ".") {
            throw FieldAlreadyPlayedException(row, column, Sign.from(existingSignAtField))
        }

        winner?.let { winner ->
            throw CannotPlayAfterFinishedGameException(winner)
        }

        val index = getBoardCharacterIndex(row, column)
        board = board.replaceRange(index..index, currentPlayer.name)

        if (hasWon(currentPlayer)) {
            this.winner = currentPlayer
            gameListener?.onVictory(currentPlayer)
        } else if (isBoardFull()) {
            this.hasEndedInDraw = true
            gameListener?.onDraw()
        } else {
            currentPlayer = if (currentPlayer == Sign.X) Sign.O else Sign.X
            gameListener?.onGameChanged()
        }
    }

    fun logBoard(): String {
        return board.run {
            substring(0..2) + "\n" + substring(3..5) + "\n" + substring(6..8)
        }
    }

    fun reset() {
        this.board = INITIAL_BOARD
        this.currentPlayer = Sign.X
        this.winner = null
        this.hasEndedInDraw = false

        gameListener?.onGameChanged()
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

    private fun isBoardFull() = board.none { it == '.' }

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
