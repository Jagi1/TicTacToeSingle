package pl.sbandurski.tictactoesingle.model

data class CGame(
    var turn: Boolean = true,
    var turnNr: Int = 0
)