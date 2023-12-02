package dev.matko.tictactoe.presentation

import dev.matko.tictactoe.domain.Game
import java.io.PrintStream

class Cli(
    printStream: PrintStream? = null
) {
    private var game: Game? = null

    init {
        if (printStream != null) {
            System.setOut(printStream)
        }
    }

    fun printGame() {
        game?.let { game ->
            println(game.logBoard())
        }
    }

    fun useGame(game: Game) {
        this.game = game
        printGame()
    }
}
