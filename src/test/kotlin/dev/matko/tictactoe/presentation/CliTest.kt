package dev.matko.tictactoe.presentation

import dev.matko.tictactoe.domain.Game
import org.junit.jupiter.api.BeforeEach
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

class CliTest {

    private lateinit var cli: Cli
    private lateinit var testOutputStream: OutputStream

    @BeforeEach
    fun setUp() {
        this.testOutputStream = ByteArrayOutputStream()
        this.cli = Cli(printStream = PrintStream(testOutputStream))
    }

    @Test
    fun `Show empty board when the game starts`() {
        val victoryListener = Game.victoryListener(doOnGameChanged = { this.cli.printGame() })
        val game = Game(victoryListener = victoryListener)

        this.cli.useGame(game)

        assertEquals("...\n...\n...\n", this.testOutputStream.toString())
    }

}