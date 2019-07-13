package pl.sbandurski.tictactoesingle.model

data class CPlayer(
    val name: String,
    val mark: String,
    val buttonsSelected: ArrayList<Int> = ArrayList()
)