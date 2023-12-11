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
                val (row, column) = input.split(",").map { it.toInt() }
                game.play(row, column)
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
        TODO("Not yet implemented")
    }

    override fun onGameChanged() {
        screenUpdateListener?.onScreenUpdate(game.logBoard() + "\n")
    }
}
