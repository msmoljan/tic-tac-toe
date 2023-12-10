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
    private lateinit var screenUpdateMemorizer: ScreenUpdateMemorizer

    class ScreenUpdateMemorizer : Cli.ScreenUpdateListener {
        var latestScreenState: String = ""
            private set

        override fun onScreenUpdate(screen: String) {
            this.latestScreenState = screen
        }
    }

    @BeforeEach
    fun setUp() {
        this.cli = Cli()
        this.screenUpdateMemorizer = ScreenUpdateMemorizer()
        this.cli.setScreenUpdateListener(screenUpdateMemorizer)
    }

    @Test
    fun `Show empty board when the game starts`() {
        assertEquals("...\n...\n...\n", getCurrentScreen())
    }

    @Test
    fun `Show proper game state after multiple turns`() {
        this.cli.processInput("1,1")
        this.cli.processInput("2,2")

        assertEquals("X..\n.O.\n...\n", getCurrentScreen())
    }

    private fun getCurrentScreen(): String {
        return screenUpdateMemorizer.latestScreenState
    }
}