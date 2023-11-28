package dev.matko.tictactoe.domain.exceptions

import dev.matko.tictactoe.domain.Sign

class FieldAlreadyPlayedException(row: Int, column: Int, sign: Sign) :
    Throwable("The field at (row: $row, column: $column) has already been played by ${sign.name}")