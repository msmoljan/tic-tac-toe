package dev.matko.tictactoe

class NonexistentFieldException(row: Int, column: Int)
    : Throwable("Row and column must be in range [1, 3], but the values were (row: $row, column: $column)")
