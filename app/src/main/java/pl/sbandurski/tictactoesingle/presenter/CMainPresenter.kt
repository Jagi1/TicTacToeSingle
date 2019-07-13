package pl.sbandurski.tictactoesingle.presenter

import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.Button
import pl.sbandurski.tictactoesingle.R
import pl.sbandurski.tictactoesingle.model.CGame
import pl.sbandurski.tictactoesingle.model.CPlayer

class CMainPresenter(
    private val view: IMainContract.IView
) : IMainContract.IPresenter {
    override fun onViewCreated() {
        Log.d("CMainPresenter", "$view created")
    }

    override fun onButtonClicked(
        button: Button,
        player1: CPlayer,
        player2: CPlayer,
        game: CGame,
        buttons: ArrayList<Button>
    ) {
        Log.d("CMainPresenter", "${button.tag} clicked")
        when (game.turn) {
            true -> {
                player1.buttonsSelected.add(button.tag.toString().toInt())
                button.text = player1.mark
                DrawableCompat.setTint(
                    DrawableCompat.wrap(button.background),
                    ContextCompat.getColor(button.context, R.color.colorPlayer1)
                )
            }
            false -> {
                player2.buttonsSelected.add(button.tag.toString().toInt())
                button.text = player2.mark
                DrawableCompat.setTint(
                    DrawableCompat.wrap(button.background),
                    ContextCompat.getColor(button.context, R.color.colorPlayer2)
                )
            }
        }
        button.isClickable = false
        button.isFocusable = false
        game.turn = !game.turn
        ++game.turnNr
        checkGameState(button, player1, player2, game, buttons)
    }

    override fun checkGameState(
        button: Button,
        player1: CPlayer,
        player2: CPlayer,
        game: CGame,
        buttons: ArrayList<Button>
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
                Snackbar.make(button, this, Snackbar.LENGTH_INDEFINITE)
                    .setAction(
                        "Reset",
                        View.OnClickListener { initGame(buttons, player1, player2, game) })
                    .show()
            } else if (!game.turn) {
                val notClickedButtons: ArrayList<Button> = ArrayList()
                buttons.forEach { button ->
                    if (button.isClickable) notClickedButtons.add(button)
                }
                onButtonClicked(notClickedButtons.random(), player1, player2, game, buttons)
            }
        }
    }

    override fun initGame(
        buttons: ArrayList<Button>,
        player1: CPlayer,
        player2: CPlayer,
        game: CGame
    ) {
        buttons.forEach { button ->
            button.text = ""
            button.isClickable = true
            button.isFocusable = true
            DrawableCompat.setTint(
                DrawableCompat.wrap(button.background),
                ContextCompat.getColor(button.context, R.color.colorDefault)
            )
        }
        player1.buttonsSelected.clear()
        player2.buttonsSelected.clear()
        game.turn = true
        game.turnNr = 0
    }

    override fun showMenu(layout: ConstraintLayout, showedMenu: Boolean, oldLayout: ConstraintSet) {
        val constraintSet2 = ConstraintSet()
        constraintSet2.clone(layout)
        if (showedMenu) {
            TransitionManager.beginDelayedTransition(layout)
            oldLayout.applyTo(layout)
        } else {
            constraintSet2.connect(R.id.main_iv, ConstraintSet.START, R.id.main_tl, ConstraintSet.END)
            constraintSet2.connect(R.id.main_iv, ConstraintSet.END, R.id.parent, ConstraintSet.END)
            constraintSet2.connect(R.id.main_iv, ConstraintSet.TOP, R.id.main_tl, ConstraintSet.TOP)
            constraintSet2.connect(R.id.main_iv, ConstraintSet.BOTTOM, R.id.main_cv, ConstraintSet.TOP)
            constraintSet2.connect(R.id.main_cv, ConstraintSet.TOP, R.id.main_tl, ConstraintSet.BOTTOM)
            constraintSet2.setRotation(R.id.main_iv, 180f)
            TransitionManager.beginDelayedTransition(layout)
            constraintSet2.applyTo(layout)
        }
    }

    override fun onDestroy() {
        Log.d("CMainPresenter", "$view destroyed")
    }
}