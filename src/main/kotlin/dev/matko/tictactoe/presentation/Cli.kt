package dev.matko.tictactoe.presentation

import dev.matko.tictactoe.domain.Game
import dev.matko.tictactoe.domain.Sign

private const val INSTRUCTION_TEXT = "\nEnter <ROW>,<COLUMN> (e.g. \"1,3\") to play, Q to quit, R to reset:\n"

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
            "[1-3],[1-3]".let { input.matches(Regex(it)) } -> parseCoordinatesAndPlay(input)
            "q" == input.lowercase() -> notifyWithGoodbyeAndQuit()
            "r" == input.lowercase() -> game.reset()
            "" == input -> refreshScreen()
            else -> notifyAboutInvalidInput(input)

        }
    }

    private fun parseCoordinatesAndPlay(input: String) {
        if (game.isFinished) {
            notifyAboutPlayingAfterFinishedGame()
        } else {
            val (row, column) = input.split(",").map { it.toInt() }
            playField(row, column)
        }
    }

    private fun notifyWithGoodbyeAndQuit() {
        screenUpdateListener?.onScreenUpdate("Goodbye!\n")
        screenUpdateListener?.onQuit()
    }

    private fun refreshScreen() {
        screenUpdateListener?.onScreenUpdate(game.logBoard() + "\n" + INSTRUCTION_TEXT)
    }

    private fun notifyAboutInvalidInput(input: String) {
        screenUpdateListener?.onScreenUpdate(game.logBoard() + "\nInvalid input: \"$input\".\n" + INSTRUCTION_TEXT)
    }

    fun setScreenUpdateListener(screenUpdateListener: ScreenUpdateListener?) {
        this.screenUpdateListener = screenUpdateListener
        onGameChanged()
    }

    override fun onVictory(sign: Sign) {
        this.screenUpdateListener?.onScreenUpdate("${game.logBoard()}\n\nGame finished: $sign won!\n" + INSTRUCTION_TEXT)
    }

    override fun onGameChanged() {
        screenUpdateListener?.onScreenUpdate(game.logBoard() + "\n\nIt's ${game.currentPlayer}'s turn\n" + INSTRUCTION_TEXT)
    }

    private fun notifyAboutPlayingAfterFinishedGame() {
        screenUpdateListener?.onScreenUpdate(
            game
                .logBoard()
                .plus("\n\nCannot play after the game has been won! Enter 'R' to restart or 'Q' to quit.\n")
        )
    }

    private fun playField(row: Int, column: Int) {
        val field = game.logBoard().replace("\n", "").get(((row - 1) * 3) + (column - 1))

        if (field != '.') {
            screenUpdateListener?.onScreenUpdate(
                game.logBoard() + "\n\nCannot play the same field twice! It's ${game.currentPlayer}'s turn\n" + INSTRUCTION_TEXT
            )
        } else {
            game.play(row, column)
        }
    }
}
