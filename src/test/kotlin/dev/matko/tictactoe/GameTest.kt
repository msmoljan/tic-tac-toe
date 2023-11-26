package dev.matko.tictactoe

import dev.matko.tictactoe.exceptions.PlayedTwiceException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GameTest {

    lateinit var game: Game

    @Test
    fun `A player can add an X to a field`() {
        val game = Game()

        game.playX(row = 1, column = 1)

        assertEquals("X..\n...\n...", game.logBoard())
    }

    @Test
    fun `A player can't add an X twice in a row`() {
        val game = Game()

        game.playX(row = 1, column = 1)

        assertThrows<PlayedTwiceException> {
            game.playX(row = 1, column = 3)
        }
    }

    @Test
    fun `The game is won if the first row is all in the same sign`() {
        val victoryListener = object : Game.VictoryListener {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(victoryListener)

        game.playX(row = 1, column = 1)
        game.playO(row = 2, column = 1)
        game.playX(row = 1, column = 2)
        game.playO(row = 2, column = 2)
        game.playX(row = 1, column = 3)

        assertEquals(1, victoryListener.xWins)
    }
}