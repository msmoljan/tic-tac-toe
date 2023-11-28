package dev.matko.tictactoe.domain.exceptions

class PlayedTwiceException : Throwable("The same player played twice, which is an illegal move") {

}
