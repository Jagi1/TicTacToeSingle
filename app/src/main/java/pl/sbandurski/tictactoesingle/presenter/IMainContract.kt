package pl.sbandurski.tictactoesingle.presenter

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import pl.sbandurski.tictactoesingle.model.CGame
import pl.sbandurski.tictactoesingle.model.CPlayer
import pl.sbandurski.tictactoesingle.view.MainActivity

/**
 * It describes communication between view and iPresenter.
 * */
interface IMainContract {
    interface IPresenter: IBasePresenter {
        fun onViewCreated()
        fun onButtonClicked(button: ImageView, player1: CPlayer, player2: CPlayer, game: CGame, buttons: ArrayList<ImageView>, view: View, activity: MainActivity)
        fun checkGameState(view: View, player1: CPlayer, player2: CPlayer, game: CGame, buttons: ArrayList<ImageView>, activity: MainActivity)
        fun initGame(buttons: ArrayList<ImageView>, player1: CPlayer, player2: CPlayer, game: CGame, activity: MainActivity)
    }
    interface IView: IBaseView<IPresenter> {
    }
}