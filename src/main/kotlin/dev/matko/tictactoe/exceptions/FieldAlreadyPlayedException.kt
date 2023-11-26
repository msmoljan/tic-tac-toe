package dev.matko.tictactoe.exceptions

import dev.matko.tictactoe.Sign

class FieldAlreadyPlayedException(row: Int, column: Int, sign: Sign) :
    Throwable("The field at (row: $row, column: $column) has already been played by ${sign.name}")