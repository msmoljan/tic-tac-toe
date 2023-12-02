package dev.matko.tictactoe.presentation

import dev.matko.tictactoe.domain.Game
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

class CliTest {

    @Test
    fun `Show empty board when the game starts`() {
        val testOutputStream = ByteArrayOutputStream()
        val cli = Cli(printStream = PrintStream(testOutputStream))
        val victoryListener = Game.victoryListener(doOnGameChanged = { cli.printGame() })
        val game = Game(victoryListener = victoryListener)

        cli.useGame(game)

        assertEquals("...\n...\n...\n", testOutputStream.toString())
    }

}