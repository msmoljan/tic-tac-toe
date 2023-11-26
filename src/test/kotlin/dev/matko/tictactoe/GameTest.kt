package dev.matko.tictactoe

import dev.matko.tictactoe.exceptions.CannotPlayAfterFinishedGameException
import dev.matko.tictactoe.exceptions.FieldAlreadyPlayedException
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

    @Test
    fun `The game is won if the second row is all in the same sign`() {
        val victoryListener = object : Game.VictoryListener {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(victoryListener)

        game.playX(row = 2, column = 1)
        game.playO(row = 1, column = 1)
        game.playX(row = 2, column = 2)
        game.playO(row = 1, column = 2)
        game.playX(row = 2, column = 3)

        assertEquals(1, victoryListener.xWins)
    }

    @Test
    fun `The game is won if the third row is all in the same sign`() {
        val victoryListener = object : Game.VictoryListener {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(victoryListener)

        game.playX(row = 3, column = 1)
        game.playO(row = 1, column = 1)
        game.playX(row = 3, column = 2)
        game.playO(row = 1, column = 2)
        game.playX(row = 3, column = 3)

        assertEquals(1, victoryListener.xWins)
    }

    @Test
    fun `The game is won if the first column is all in the same sign`() {
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
        game.playO(row = 1, column = 2)
        game.playX(row = 2, column = 1)
        game.playO(row = 1, column = 3)
        game.playX(row = 3, column = 1)

        assertEquals(1, victoryListener.xWins)
    }

    @Test
    fun `The game is won if the second column is all in the same sign`() {
        val victoryListener = object : Game.VictoryListener {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(victoryListener)

        game.playX(row = 1, column = 2)
        game.playO(row = 1, column = 1)
        game.playX(row = 2, column = 2)
        game.playO(row = 2, column = 1)
        game.playX(row = 3, column = 2)

        assertEquals(1, victoryListener.xWins)
    }

    @Test
    fun `The game is won if the third column is all in the same sign`() {
        val victoryListener = object : Game.VictoryListener {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(victoryListener)

        game.playX(row = 1, column = 3)
        game.playO(row = 1, column = 1)
        game.playX(row = 2, column = 3)
        game.playO(row = 2, column = 1)
        game.playX(row = 3, column = 3)

        assertEquals(1, victoryListener.xWins)
    }

    @Test
    fun `The game is won if the left-to-right diagonal is all in the same sign`() {
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
        game.playO(row = 1, column = 2)
        game.playX(row = 2, column = 2)
        game.playO(row = 2, column = 1)
        game.playX(row = 3, column = 3)

        assertEquals(1, victoryListener.xWins)
    }

    @Test
    fun `The game is won if the right-to-left diagonal is all in the same sign`() {
        val victoryListener = object : Game.VictoryListener {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(victoryListener)

        game.playX(row = 1, column = 3)
        game.playO(row = 1, column = 2)
        game.playX(row = 2, column = 2)
        game.playO(row = 2, column = 1)
        game.playX(row = 3, column = 1)

        assertEquals(1, victoryListener.xWins)
    }

    @Test
    fun `A field cannot be played twice in a game`() {
        val game = Game()

        game.playX(row = 2, column = 2)

        assertThrows<FieldAlreadyPlayedException> {
            game.playO(row = 2, column = 2)
        }
    }

    @Test
    fun `A row number cannot be smaller than 1`() {
        val game = Game()

        assertThrows<NonexistentFieldException> {
            game.playX(row = 0, column = 2)
        }
    }

    @Test
    fun `A column number cannot be smaller than 1`() {
        val game = Game()

        assertThrows<NonexistentFieldException> {
            game.playX(row = 1, column = 0)
        }
    }

    @Test
    fun `A row number cannot be larger than 3`() {
        val game = Game()

        assertThrows<NonexistentFieldException> {
            game.playX(row = 4, column = 1)
        }
    }

    @Test
    fun `A column number cannot be larger than 3`() {
        val game = Game()

        assertThrows<NonexistentFieldException> {
            game.playX(row = 3, column = 4)
        }
    }

    @Test
    fun `Cannot play after the game is won`() {
        val game = Game()

        game.playX(row = 1, column = 1)
        game.playO(row = 2, column = 1)
        game.playX(row = 1, column = 2)
        game.playO(row = 2, column = 2)
        game.playX(row = 1, column = 3)

        assertThrows<CannotPlayAfterFinishedGameException> {
            game.playO(row = 3, column = 3)
        }
    }

    @Test
    fun `The board is emptied after the game has been reset`() {
        val game = Game()

        game.playX(row = 1, column = 1)
        game.playO(row = 2, column = 1)
        game.reset()

        assertEquals("...\n...\n...", game.logBoard())
    }

    @Test
    fun `X can play after a game reset`() {
        val game = Game()

        game.playX(row = 1, column = 1)
        game.reset()
        game.playX(row = 2, column = 1)

        assertEquals("...\nX..\n...", game.logBoard())
    }
}