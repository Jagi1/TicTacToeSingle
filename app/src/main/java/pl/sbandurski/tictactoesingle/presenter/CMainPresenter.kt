package pl.sbandurski.tictactoesingle.presenter

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Handler
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_game.view.*
import pl.sbandurski.tictactoesingle.R
import pl.sbandurski.tictactoesingle.model.CGame
import pl.sbandurski.tictactoesingle.model.CPlayer
import pl.sbandurski.tictactoesingle.view.MainActivity
import kotlin.random.Random

class CMainPresenter(
    private val view: IMainContract.IView
) : IMainContract.IPresenter {
    override fun onViewCreated() {
        Log.d("CMainPresenter", "$view created")
    }

    override fun onButtonClicked(
        button: ImageView,
        player1: CPlayer,
        player2: CPlayer,
        game: CGame,
        buttons: ArrayList<ImageView>,
        view: View,
        activity: MainActivity
    ) {
        Log.d("CMainPresenter", "${button.tag} clicked")
        when (game.turn) {
            true -> {
                player1.buttonsSelected.add(button.tag.toString().toInt())
                button.setImageDrawable(activity.resources.getDrawable(R.drawable.animated_circle))
                val avd: AnimatedVectorDrawable = button.drawable as AnimatedVectorDrawable
                avd.start()
            }
            false -> {
                player2.buttonsSelected.add(button.tag.toString().toInt())
                button.setImageDrawable(activity.resources.getDrawable(R.drawable.animated_x))
                val avd: AnimatedVectorDrawable = button.drawable as AnimatedVectorDrawable
                avd.start()
            }
        }
        val runnable = Runnable {
            kotlin.run {
                button.isClickable = false
                button.isFocusable = false
                game.turn = !game.turn
                ++game.turnNr
                checkGameState(view, player1, player2, game, buttons, activity)
            }
        }
        Handler().postDelayed(runnable, 1000)
    }

    override fun checkGameState(
        view: View,
        player1: CPlayer,
        player2: CPlayer,
        game: CGame,
        buttons: ArrayList<ImageView>,
        activity: MainActivity
    ) {
        var end = 0
        with(player1.buttonsSelected) {
            when {
                containsAll(arrayListOf(1, 2, 3)) -> end = 1
                containsAll(arrayListOf(4, 5, 6)) -> end = 1
                containsAll(arrayListOf(7, 8, 9)) -> end = 1
                containsAll(arrayListOf(1, 4, 7)) -> end = 1
                containsAll(arrayListOf(2, 5, 8)) -> end = 1
                containsAll(arrayListOf(3, 6, 9)) -> end = 1
                containsAll(arrayListOf(1, 5, 9)) -> end = 1
                containsAll(arrayListOf(3, 5, 7)) -> end = 1
            }
        }
        with(player2.buttonsSelected) {
            when {
                containsAll(arrayListOf(1, 2, 3)) -> end = 2
                containsAll(arrayListOf(4, 5, 6)) -> end = 2
                containsAll(arrayListOf(7, 8, 9)) -> end = 2
                containsAll(arrayListOf(1, 4, 7)) -> end = 2
                containsAll(arrayListOf(2, 5, 8)) -> end = 2
                containsAll(arrayListOf(3, 6, 9)) -> end = 2
                containsAll(arrayListOf(1, 5, 9)) -> end = 2
                containsAll(arrayListOf(3, 5, 7)) -> end = 2
            }
        }
        when {
            end == 0 && game.turnNr == 9 -> "Tie"
            end == 1 -> "${player1.name} won"
            end == 2 -> "${player2.name} won"
            else -> ""
        }.apply {
            if (this != "") {
                buttons.forEach { button ->
                    button.isClickable = false
                    button.isFocusable = false
                }
                Toast.makeText(view.context, this, Toast.LENGTH_LONG).show()
                when (this) {
                    "${player1.name} won" -> {
                        var newScore = view.game_cv_cl_tv1.text.toString().toInt()
                        ++newScore
                        view.game_cv_cl_tv1.text = newScore.toString()
                    }
                    "${player2.name} won" -> {
                        var newScore = view.game_cv_cl_tv3.text.toString().toInt()
                        ++newScore
                        view.game_cv_cl_tv3.text = newScore.toString()
                    }
                }
            } else if (!game.turn) {
                val notClickedButtons: ArrayList<ImageView> = ArrayList()
                val notClickedButtonsMap: HashMap<Int, ImageView> = HashMap()
                buttons.forEach { button ->
                    if (button.isClickable) {
                        notClickedButtons.add(button)
                        notClickedButtonsMap[button.tag.toString().toInt()] = button
                    }
                }
                val difficulty = activity.difficulty
                val range = Random.nextInt(1, 3)
                when {
                    // Win conditions
                    player2.buttonsSelected.containsAll(arrayListOf(1, 5)) && notClickedButtonsMap.containsKey(9) -> onButtonClicked(notClickedButtonsMap[9]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(1, 9)) && notClickedButtonsMap.containsKey(5) -> onButtonClicked(notClickedButtonsMap[5]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(5, 9)) && notClickedButtonsMap.containsKey(1) -> onButtonClicked(notClickedButtonsMap[1]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(3, 5)) && notClickedButtonsMap.containsKey(9) -> onButtonClicked(notClickedButtonsMap[9]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(3, 7)) && notClickedButtonsMap.containsKey(5) -> onButtonClicked(notClickedButtonsMap[5]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(5, 7)) && notClickedButtonsMap.containsKey(3) -> onButtonClicked(notClickedButtonsMap[3]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(1, 2)) && notClickedButtonsMap.containsKey(3) -> onButtonClicked(notClickedButtonsMap[3]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(1, 3)) && notClickedButtonsMap.containsKey(2) -> onButtonClicked(notClickedButtonsMap[2]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(2, 3)) && notClickedButtonsMap.containsKey(1) -> onButtonClicked(notClickedButtonsMap[1]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(4, 5)) && notClickedButtonsMap.containsKey(6) -> onButtonClicked(notClickedButtonsMap[6]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(4, 6)) && notClickedButtonsMap.containsKey(5) -> onButtonClicked(notClickedButtonsMap[5]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(5, 6)) && notClickedButtonsMap.containsKey(4) -> onButtonClicked(notClickedButtonsMap[4]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(7, 8)) && notClickedButtonsMap.containsKey(9) -> onButtonClicked(notClickedButtonsMap[9]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(7, 9)) && notClickedButtonsMap.containsKey(8) -> onButtonClicked(notClickedButtonsMap[8]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(8, 9)) && notClickedButtonsMap.containsKey(7) -> onButtonClicked(notClickedButtonsMap[7]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(1, 4)) && notClickedButtonsMap.containsKey(7) -> onButtonClicked(notClickedButtonsMap[7]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(1, 7)) && notClickedButtonsMap.containsKey(4) -> onButtonClicked(notClickedButtonsMap[4]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(4, 7)) && notClickedButtonsMap.containsKey(1) -> onButtonClicked(notClickedButtonsMap[1]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(2, 5)) && notClickedButtonsMap.containsKey(8) -> onButtonClicked(notClickedButtonsMap[8]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(2, 8)) && notClickedButtonsMap.containsKey(5) -> onButtonClicked(notClickedButtonsMap[5]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(5, 8)) && notClickedButtonsMap.containsKey(2) -> onButtonClicked(notClickedButtonsMap[2]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(3, 6)) && notClickedButtonsMap.containsKey(9) -> onButtonClicked(notClickedButtonsMap[9]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(3, 9)) && notClickedButtonsMap.containsKey(6) -> onButtonClicked(notClickedButtonsMap[6]!!, player1, player2, game, buttons, view, activity)
                    player2.buttonsSelected.containsAll(arrayListOf(6, 9)) && notClickedButtonsMap.containsKey(3) -> onButtonClicked(notClickedButtonsMap[3]!!, player1, player2, game, buttons, view, activity)
                    // Random
                    difficulty == 1 -> onButtonClicked(notClickedButtons.random(), player1, player2, game, buttons, view, activity)
                    difficulty == 2 && range == 1 -> onButtonClicked(notClickedButtons.random(), player1, player2, game, buttons, view, activity)
                    // Blocking enemy win condition
                    player1.buttonsSelected.containsAll(arrayListOf(1, 5)) && notClickedButtonsMap.containsKey(9) -> onButtonClicked(notClickedButtonsMap[9]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(1, 9)) && notClickedButtonsMap.containsKey(5) -> onButtonClicked(notClickedButtonsMap[5]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(5, 9)) && notClickedButtonsMap.containsKey(1) -> onButtonClicked(notClickedButtonsMap[1]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(3, 5)) && notClickedButtonsMap.containsKey(9) -> onButtonClicked(notClickedButtonsMap[9]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(3, 7)) && notClickedButtonsMap.containsKey(5) -> onButtonClicked(notClickedButtonsMap[5]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(5, 7)) && notClickedButtonsMap.containsKey(3) -> onButtonClicked(notClickedButtonsMap[3]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(1, 2)) && notClickedButtonsMap.containsKey(3) -> onButtonClicked(notClickedButtonsMap[3]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(1, 3)) && notClickedButtonsMap.containsKey(2) -> onButtonClicked(notClickedButtonsMap[2]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(2, 3)) && notClickedButtonsMap.containsKey(1) -> onButtonClicked(notClickedButtonsMap[1]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(4, 5)) && notClickedButtonsMap.containsKey(6) -> onButtonClicked(notClickedButtonsMap[6]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(4, 6)) && notClickedButtonsMap.containsKey(5) -> onButtonClicked(notClickedButtonsMap[5]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(5, 6)) && notClickedButtonsMap.containsKey(4) -> onButtonClicked(notClickedButtonsMap[4]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(7, 8)) && notClickedButtonsMap.containsKey(9) -> onButtonClicked(notClickedButtonsMap[9]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(7, 9)) && notClickedButtonsMap.containsKey(8) -> onButtonClicked(notClickedButtonsMap[8]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(8, 9)) && notClickedButtonsMap.containsKey(7) -> onButtonClicked(notClickedButtonsMap[7]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(1, 4)) && notClickedButtonsMap.containsKey(7) -> onButtonClicked(notClickedButtonsMap[7]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(1, 7)) && notClickedButtonsMap.containsKey(4) -> onButtonClicked(notClickedButtonsMap[4]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(4, 7)) && notClickedButtonsMap.containsKey(1) -> onButtonClicked(notClickedButtonsMap[1]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(2, 5)) && notClickedButtonsMap.containsKey(8) -> onButtonClicked(notClickedButtonsMap[8]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(2, 8)) && notClickedButtonsMap.containsKey(5) -> onButtonClicked(notClickedButtonsMap[5]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(5, 8)) && notClickedButtonsMap.containsKey(2) -> onButtonClicked(notClickedButtonsMap[2]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(3, 6)) && notClickedButtonsMap.containsKey(9) -> onButtonClicked(notClickedButtonsMap[9]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(3, 9)) && notClickedButtonsMap.containsKey(6) -> onButtonClicked(notClickedButtonsMap[6]!!, player1, player2, game, buttons, view, activity)
                    player1.buttonsSelected.containsAll(arrayListOf(6, 9)) && notClickedButtonsMap.containsKey(3) -> onButtonClicked(notClickedButtonsMap[3]!!, player1, player2, game, buttons, view, activity)
                    // Rest
                    notClickedButtonsMap.contains(5) -> onButtonClicked(notClickedButtonsMap[5]!!, player1, player2, game, buttons, view, activity)
                    else -> onButtonClicked(notClickedButtons.random(), player1, player2, game, buttons, view, activity)
                }
            }
        }
    }

    override fun initGame(
        buttons: ArrayList<ImageView>,
        player1: CPlayer,
        player2: CPlayer,
        game: CGame,
        activity: MainActivity
    ) {
        buttons.forEach { button ->
            button.setImageResource(android.R.color.transparent)
            button.isClickable = true
            button.isFocusable = true
            button.setBackgroundColor(activity.resources.getColor(R.color.colorPrimaryDark))
        }
        player1.buttonsSelected.clear()
        player2.buttonsSelected.clear()
        game.turn = true
        game.turnNr = 0
    }

    override fun onDestroy() {
        Log.d("CMainPresenter", "$view destroyed")
    }
}