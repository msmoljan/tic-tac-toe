package dev.matko.tictactoe.presentation

import dev.matko.tictactoe.domain.Game
import java.io.PrintStream

private const val CLEAR_CONSOLE_CHARS = "\\033[9A"

class Cli(
    private val printStream: PrintStream = System.out
) {
    private var game: Game? = null

    init {
        System.setOut(printStream)
    }

    fun printGame() {
        game?.let { game ->
            clearConsole()
            println(game.logBoard())
        }
    }

    private fun clearConsole() {
        printStream.println(CLEAR_CONSOLE_CHARS) // Clear the console
    }

    fun useGame(game: Game) {
        this.game = game
        printGame()
    }

    fun processInput(input: String) {
        val (row, column) = input.split(",").map { it.toInt() }

        game?.play(row, column)
    }
}
