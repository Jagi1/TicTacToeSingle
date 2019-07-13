package pl.sbandurski.tictactoesingle.presenter

interface IBaseView<T> {
    fun setPresenter(presenter: T)
}