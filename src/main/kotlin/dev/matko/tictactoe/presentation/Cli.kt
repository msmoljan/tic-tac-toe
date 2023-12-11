package dev.matko.tictactoe.presentation

import dev.matko.tictactoe.domain.Game
import dev.matko.tictactoe.domain.Sign


class Cli : Game.GameListener {

    interface ScreenUpdateListener {
        fun onScreenUpdate(screen: String)
        fun onQuit()
    }

    private var game: Game
    private var screenUpdateListener: ScreenUpdateListener? = null

    init {
        this.game = Game(gameListener = this)
    }

    @Suppress("NAME_SHADOWING")
    fun processInput(input: String) {
        val input = input.replace(regex = Regex("\\s"), replacement = "")

        when {
            input.matches(Regex("[1-3],[1-3]")) -> {
                if (game.isFinished) {
                    screenUpdateListener?.onScreenUpdate(
                        game
                            .logBoard()
                            .plus("\n\nCannot play after the game has been won! Enter 'R' to restart or 'Q' to quit.\n")
                    )
                } else {
                    val (row, column) = input.split(",").map { it.toInt() }
                    game.play(row, column)
                }
            }

            "q" == input.lowercase() -> {
                screenUpdateListener?.onScreenUpdate("Goodbye!\n")
                screenUpdateListener?.onQuit()
            }

            "r" == input.lowercase() -> game.reset()

            "" == input -> {
                screenUpdateListener?.onScreenUpdate(game.logBoard() + "\n")
            }

            else -> {
                screenUpdateListener?.onScreenUpdate(game.logBoard() + "\nInvalid input: \"$input\".\n")
            }
        }
    }

    fun setScreenUpdateListener(screenUpdateListener: ScreenUpdateListener?) {
        this.screenUpdateListener = screenUpdateListener
        onGameChanged()
    }

    override fun onVictory(sign: Sign) {
        this.screenUpdateListener?.onScreenUpdate("${game.logBoard()}\n\nGame finished: X won!\n")
    }

    override fun onGameChanged() {
        screenUpdateListener?.onScreenUpdate(game.logBoard() + "\n\nIt's ${game.currentPlayer}'s turn\n")
    }
}
