package dev.matko.tictactoe.domain.exceptions

import dev.matko.tictactoe.domain.Sign

class CannotPlayAfterFinishedGameException(winner: Sign) : Throwable("The game was already won by ${winner.name}, so it is illegal to make a further move.") {

}
