package dev.matko.tictactoe.domain.exceptions

class CannotPlayAfterDrawException : Throwable(
    "The game has already ended in a draw, so it is illegal to make a further move."
)
