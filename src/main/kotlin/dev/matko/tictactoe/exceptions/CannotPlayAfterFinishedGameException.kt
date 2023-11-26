package dev.matko.tictactoe.exceptions

import dev.matko.tictactoe.Sign

class CannotPlayAfterFinishedGameException(winner: Sign) : Throwable("The game was already won by ${winner.name}, so it is illegal to make a further move.") {

}
