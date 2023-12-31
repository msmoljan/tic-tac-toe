package dev.matko.tictactoe.domain

import dev.matko.tictactoe.domain.exceptions.CannotPlayAfterDrawException
import dev.matko.tictactoe.domain.exceptions.CannotPlayAfterFinishedGameException
import dev.matko.tictactoe.domain.exceptions.FieldAlreadyPlayedException
import dev.matko.tictactoe.domain.exceptions.NonexistentFieldException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertTrue

@DisplayName("Gameplay tests")
class GameTest {

    private open class NoOpGameListener : Game.GameListener {
        override fun onGameChanged() = Unit
        override fun onVictory(sign: Sign) = Unit
        override fun onDraw() = Unit
    }

    @Test
    fun `A player can add an X to a field`() {
        val game = Game()

        game.play(row = 1, column = 1)

        assertEquals("X..\n...\n...", game.logBoard())
    }

    @Test
    fun `The game is won if the first row is all in the same sign`() {
        val gameListener = object : NoOpGameListener() {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(gameListener)

        game.play(row = 1, column = 1)
        game.play(row = 2, column = 1)
        game.play(row = 1, column = 2)
        game.play(row = 2, column = 2)
        game.play(row = 1, column = 3)

        assertEquals(1, gameListener.xWins)
    }

    @Test
    fun `The game is won if the second row is all in the same sign`() {
        val gameListener = object : NoOpGameListener() {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(gameListener)

        game.play(row = 2, column = 1)
        game.play(row = 1, column = 1)
        game.play(row = 2, column = 2)
        game.play(row = 1, column = 2)
        game.play(row = 2, column = 3)

        assertEquals(1, gameListener.xWins)
    }

    @Test
    fun `The game is won if the third row is all in the same sign`() {
        val gameListener = object : NoOpGameListener() {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(gameListener)

        game.play(row = 3, column = 1)
        game.play(row = 1, column = 1)
        game.play(row = 3, column = 2)
        game.play(row = 1, column = 2)
        game.play(row = 3, column = 3)

        assertEquals(1, gameListener.xWins)
    }

    @Test
    fun `The game is won if the first column is all in the same sign`() {
        val gameListener = object : NoOpGameListener() {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(gameListener)

        game.play(row = 1, column = 1)
        game.play(row = 1, column = 2)
        game.play(row = 2, column = 1)
        game.play(row = 1, column = 3)
        game.play(row = 3, column = 1)

        assertEquals(1, gameListener.xWins)
    }

    @Test
    fun `The game is won if the second column is all in the same sign`() {
        val gameListener = object : NoOpGameListener() {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(gameListener)

        game.play(row = 1, column = 2)
        game.play(row = 1, column = 1)
        game.play(row = 2, column = 2)
        game.play(row = 2, column = 1)
        game.play(row = 3, column = 2)

        assertEquals(1, gameListener.xWins)
    }

    @Test
    fun `The game is won if the third column is all in the same sign`() {
        val gameListener = object : NoOpGameListener() {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(gameListener)

        game.play(row = 1, column = 3)
        game.play(row = 1, column = 1)
        game.play(row = 2, column = 3)
        game.play(row = 2, column = 1)
        game.play(row = 3, column = 3)

        assertEquals(1, gameListener.xWins)
    }

    @Test
    fun `The game is won if the left-to-right diagonal is all in the same sign`() {
        val gameListener = object : NoOpGameListener() {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(gameListener)

        game.play(row = 1, column = 1)
        game.play(row = 1, column = 2)
        game.play(row = 2, column = 2)
        game.play(row = 2, column = 1)
        game.play(row = 3, column = 3)

        assertEquals(1, gameListener.xWins)
    }

    @Test
    fun `The game is won if the right-to-left diagonal is all in the same sign`() {
        val gameListener = object : NoOpGameListener() {
            var xWins = 0

            override fun onVictory(sign: Sign) {
                if (sign == Sign.X) {
                    xWins++
                }
            }
        }
        val game = Game(gameListener)

        game.play(row = 1, column = 3)
        game.play(row = 1, column = 2)
        game.play(row = 2, column = 2)
        game.play(row = 2, column = 1)
        game.play(row = 3, column = 1)

        assertEquals(1, gameListener.xWins)
    }

    @Test
    fun `A field cannot be played twice in a game`() {
        val game = Game()

        game.play(row = 2, column = 2)

        assertThrows<FieldAlreadyPlayedException> {
            game.play(row = 2, column = 2)
        }
    }

    @Test
    fun `A row number cannot be smaller than 1`() {
        val game = Game()

        assertThrows<NonexistentFieldException> {
            game.play(row = 0, column = 2)
        }
    }

    @Test
    fun `A column number cannot be smaller than 1`() {
        val game = Game()

        assertThrows<NonexistentFieldException> {
            game.play(row = 1, column = 0)
        }
    }

    @Test
    fun `A row number cannot be larger than 3`() {
        val game = Game()

        assertThrows<NonexistentFieldException> {
            game.play(row = 4, column = 1)
        }
    }

    @Test
    fun `A column number cannot be larger than 3`() {
        val game = Game()

        assertThrows<NonexistentFieldException> {
            game.play(row = 3, column = 4)
        }
    }

    @Test
    fun `Cannot play after the game is won`() {
        val game = Game()

        game.play(row = 1, column = 1)
        game.play(row = 2, column = 1)
        game.play(row = 1, column = 2)
        game.play(row = 2, column = 2)
        game.play(row = 1, column = 3)

        assertThrows<CannotPlayAfterFinishedGameException> {
            game.play(row = 3, column = 3)
        }
    }

    @Test
    fun `The board is emptied after the game has been reset`() {
        val game = Game()

        game.play(row = 1, column = 1)
        game.play(row = 2, column = 1)
        game.reset()

        assertEquals("...\n...\n...", game.logBoard())
    }

    @Test
    fun `X can play after a game reset`() {
        val game = Game()

        game.play(row = 1, column = 1)
        game.reset()
        game.play(row = 2, column = 1)

        assertEquals("...\nX..\n...", game.logBoard())
    }

    @Test
    fun `The game should notify its listener when it ends in a draw`() {
        val gameListener = object : NoOpGameListener() {
            var isDraw = false
            override fun onDraw() {
                this.isDraw = true
            }
        }
        val game = Game(gameListener)

        game.play(row = 1, column = 2)
        game.play(row = 1, column = 1)
        game.play(row = 2, column = 1)
        game.play(row = 1, column = 3)
        game.play(row = 2, column = 3)
        game.play(row = 2, column = 2)
        game.play(row = 3, column = 1)
        game.play(row = 3, column = 2)
        game.play(row = 3, column = 3)

        assertTrue(gameListener.isDraw)
    }

    @Test
    fun `The game should throw if an attempt at play is made after a draw`() {
        val gameListener = object : NoOpGameListener() {
            var isDraw = false
            override fun onDraw() {
                this.isDraw = true
            }
        }
        val game = Game(gameListener)

        game.play(row = 1, column = 2)
        game.play(row = 1, column = 1)
        game.play(row = 2, column = 1)
        game.play(row = 1, column = 3)
        game.play(row = 2, column = 3)
        game.play(row = 2, column = 2)
        game.play(row = 3, column = 1)
        game.play(row = 3, column = 2)
        game.play(row = 3, column = 3)

        assertThrows<CannotPlayAfterDrawException> {
            game.play(row = 3, column = 3)
        }
    }

    @Test
    fun `The game can be played again if it's reset after a draw`() {
        val gameListener = object : NoOpGameListener() {
            var isDraw = false
            override fun onDraw() {
                this.isDraw = true
            }
        }
        val game = Game(gameListener)

        game.play(row = 1, column = 2)
        game.play(row = 1, column = 1)
        game.play(row = 2, column = 1)
        game.play(row = 1, column = 3)
        game.play(row = 2, column = 3)
        game.play(row = 2, column = 2)
        game.play(row = 3, column = 1)
        game.play(row = 3, column = 2)
        game.play(row = 3, column = 3)
        game.reset()
        game.play(row = 1, column = 1)
        
        assertEquals("X..\n...\n...", game.logBoard())
    }
}