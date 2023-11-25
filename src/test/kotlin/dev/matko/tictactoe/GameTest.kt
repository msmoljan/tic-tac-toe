package dev.matko.tictactoe

import dev.matko.tictactoe.Game
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `A player can add an X to a field`() {
        val game = Game()

        game.playX(Position.TOP_LEFT)

        assertEquals("X..\n...\n...", game.board)
    }
}