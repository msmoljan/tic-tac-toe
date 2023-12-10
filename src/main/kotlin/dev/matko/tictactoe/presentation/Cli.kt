package dev.matko.tictactoe.presentation

import dev.matko.tictactoe.domain.Game
import dev.matko.tictactoe.domain.Sign
import java.io.PrintStream


class Cli : Game.GameListener {

    interface ScreenUpdateListener {
        fun onScreenUpdate(screen: String)
    }

    private var game: Game
    private var screenUpdateListener: ScreenUpdateListener? = null

    init {
        this.game = Game(gameListener = this)
    }

    fun processInput(input: String) {
        val (row, column) = input.split(",").map { it.toInt() }

        game.play(row, column)
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
