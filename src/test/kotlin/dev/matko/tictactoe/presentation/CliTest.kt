package dev.matko.tictactoe.presentation

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private const val INSTRUCTION_TEXT = "Enter <ROW>,<COLUMN> (e.g. \"1,3\") to play, Q to quit, R to reset:"

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
        cli = Cli()
        this.screenUpdateMemorizer = ScreenUpdateMemorizer()
        cli.setScreenUpdateListener(screenUpdateMemorizer)
    }

    @Test
    fun `Show empty board when the game starts`() {
        assertEquals("...\n...\n...\n\nIt's X's turn\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Show proper game state after multiple turns`() {
        cli.processInput("1,1")
        cli.processInput("2,2")

        assertEquals("X..\n.O.\n...\n\nIt's X's turn\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Press 'R' to reset game`() {
        cli.processInput("1,1")
        cli.processInput("2,2")
        cli.processInput("R")

        assertEquals("...\n...\n...\n\nIt's X's turn\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Press 'r' to reset game`() {
        cli.processInput("1,1")
        cli.processInput("2,2")
        cli.processInput("r")

        assertEquals("...\n...\n...\n\nIt's X's turn\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Allow whitespace characters anywhere in input`() {
        cli.processInput(" 1 ,1 ")
        cli.processInput("  2, 2   ")

        assertEquals("X..\n.O.\n...\n\nIt's X's turn\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `'Q' exits game`() {
        cli.processInput("1,1")
        cli.processInput("Q")

        assertEquals("Goodbye!\n", getCurrentScreen())
    }

    @Test
    fun `'q' exits game`() {
        cli.processInput("1,1")
        cli.processInput("Q")

        assertEquals("Goodbye!\n", getCurrentScreen())
        assertTrue(screenUpdateMemorizer.hasQuit)
    }

    @Test
    fun `Invalid input is communicated if wrong key is pressed`() {
        cli.processInput("2,2")
        cli.processInput("h")

        assertEquals("...\n.X.\n...\nInvalid input: \"h\".\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `The game is refreshed on empty input, but retains state`() {
        cli.processInput("2,2")
        cli.processInput("h")
        cli.processInput("")

        assertEquals("...\n.X.\n...\n\nIt's O's turn\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Prevent coordinate input after winning`() {
        cli.processInput("1,1")
        cli.processInput("1,2")
        cli.processInput("2,2")
        cli.processInput("1,3")
        cli.processInput("3,3")
        cli.processInput("3,1")

        assertEquals(
            "XOO\n.X.\n..X\n\nCannot play after the game has been won! Enter 'R' to restart or 'Q' to quit.\n",
            getCurrentScreen()
        )
    }

    @Test
    fun `Allow reset after winning`() {
        cli.processInput("1,1")
        cli.processInput("1,2")
        cli.processInput("2,2")
        cli.processInput("1,3")
        cli.processInput("3,3")
        cli.processInput("r")

        assertEquals("...\n...\n...\n\nIt's X's turn\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Allow quitting after winning`() {
        cli.processInput("1,1")
        cli.processInput("1,2")
        cli.processInput("2,2")
        cli.processInput("1,3")
        cli.processInput("3,3")
        cli.processInput("q")

        assertTrue(screenUpdateMemorizer.hasQuit)
    }

    @Test
    fun `Display proper message after X wins`() {
        cli.processInput("1,1")
        cli.processInput("1,2")
        cli.processInput("2,2")
        cli.processInput("1,3")
        cli.processInput("3,3")

        assertEquals("XOO\n.X.\n..X\n\nGame finished: X won!\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Display proper message after O wins`() {
        cli.processInput("1,2")
        cli.processInput("1,1")
        cli.processInput("2,1")
        cli.processInput("2,2")
        cli.processInput("3,1")
        cli.processInput("3,3")

        assertEquals("OX.\nXO.\nX.O\n\nGame finished: O won!\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Display proper message when the same field is played twice`() {
        cli.processInput("1,1")
        cli.processInput("1,1")

        assertEquals(
            "X..\n...\n...\n\nCannot play the same field twice! It's O's turn\n\n$INSTRUCTION_TEXT\n",
            getCurrentScreen()
        )
    }

    @Test
    fun `Display current player`() {
        cli.processInput("1,1")

        assertEquals("X..\n...\n...\n\nIt's O's turn\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Show proper message when the game ends in a draw`() {
        cli.processInput("1,2")
        cli.processInput("1,1")
        cli.processInput("2,1")
        cli.processInput("1,3")
        cli.processInput("2,3")
        cli.processInput("2,2")
        cli.processInput("3,1")
        cli.processInput("3,2")
        cli.processInput("3,3")

        assertEquals("OXO\nXOX\nXOX\n\nThe game ended in a draw.\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    @Test
    fun `Keep showing the draw message when the player tries to play after a draw`() {
        cli.processInput("1,2")
        cli.processInput("1,1")
        cli.processInput("2,1")
        cli.processInput("1,3")
        cli.processInput("2,3")
        cli.processInput("2,2")
        cli.processInput("3,1")
        cli.processInput("3,2")
        cli.processInput("3,3")
        cli.processInput("1,1")

        assertEquals("OXO\nXOX\nXOX\n\nThe game ended in a draw.\n\n$INSTRUCTION_TEXT\n", getCurrentScreen())
    }

    private fun getCurrentScreen(): String {
        return screenUpdateMemorizer.latestScreenState
    }
}