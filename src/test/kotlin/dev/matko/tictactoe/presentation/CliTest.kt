package dev.matko.tictactoe.presentation

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals

@DisplayName("CLI tests")
class CliTest {

    private lateinit var cli: Cli
    private lateinit var screenUpdateMemorizer: ScreenUpdateMemorizer

    class ScreenUpdateMemorizer : Cli.ScreenUpdateListener {
        var latestScreenState: String = ""
            private set

        var hasQuit = false

        override fun onScreenUpdate(screen: String) {
            this.latestScreenState = screen
        }

        override fun onQuit() {
            this.hasQuit = true
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

    @Test
    fun `Press 'R' to reset game`() {
        this.cli.processInput("1,1")
        this.cli.processInput("2,2")
        this.cli.processInput("R")

        assertEquals("...\n...\n...\n", getCurrentScreen())
    }

    @Test
    fun `Press 'r' to reset game`() {
        this.cli.processInput("1,1")
        this.cli.processInput("2,2")
        this.cli.processInput("r")

        assertEquals("...\n...\n...\n", getCurrentScreen())
    }

    @Test
    fun `Allow whitespace characters anywhere in input`() {
        this.cli.processInput(" 1 ,1 ")
        this.cli.processInput("  2, 2   ")

        assertEquals("X..\n.O.\n...\n", getCurrentScreen())
    }

    @Test
    fun `'Q' exits game`() {
        this.cli.processInput("1,1")
        this.cli.processInput("Q")

        assertEquals("Goodbye!\n", getCurrentScreen())
    }

    @Test
    fun `'q' exits game`() {
        this.cli.processInput("1,1")
        this.cli.processInput("Q")

        assertEquals("Goodbye!\n", getCurrentScreen())
        assertEquals(true, screenUpdateMemorizer.hasQuit)
    }

    @Test
    fun `Invalid input is communicated if wrong key is pressed`() {
        this.cli.processInput("2,2")
        this.cli.processInput("h")

        assertEquals("...\n.X.\n...\nInvalid input: \"h\".\n", getCurrentScreen())
    }

    @Test
    fun `The game is refreshed on empty input, but retains state`() {
        this.cli.processInput("2,2")
        this.cli.processInput("h")
        this.cli.processInput("")

        assertEquals("...\n.X.\n...\n", getCurrentScreen())
    }

    private fun getCurrentScreen(): String {
        return screenUpdateMemorizer.latestScreenState
    }
}