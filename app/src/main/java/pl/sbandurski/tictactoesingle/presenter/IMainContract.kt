package pl.sbandurski.tictactoesingle.presenter

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.widget.Button
import pl.sbandurski.tictactoesingle.model.CGame
import pl.sbandurski.tictactoesingle.model.CPlayer

/**
 * It describes communication between view and presenter.
 * */
interface IMainContract {
    interface IPresenter: IBasePresenter {
        fun onViewCreated()
        fun onButtonClicked(button: Button, player1: CPlayer, player2: CPlayer, game: CGame, buttons: ArrayList<Button>)
        fun checkGameState(button: Button, player1: CPlayer, player2: CPlayer, game: CGame, buttons: ArrayList<Button>)
        fun initGame(buttons: ArrayList<Button>, player1: CPlayer, player2: CPlayer, game: CGame)
        fun showMenu(layout: ConstraintLayout, showedMenu: Boolean, oldLayout: ConstraintSet)
    }
    interface IView: IBaseView<IPresenter> {
    }
}