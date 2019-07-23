package pl.sbandurski.tictactoesingle.view

import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_game.view.*
import pl.sbandurski.tictactoesingle.R
import pl.sbandurski.tictactoesingle.model.CGame
import pl.sbandurski.tictactoesingle.model.CPlayer

class GameFragment: Fragment() {

    private lateinit var player1: CPlayer
    private lateinit var player2: CPlayer
    private lateinit var game: CGame
    private lateinit var buttons: ArrayList<ImageView>
    private lateinit var oldLayout: ConstraintSet
    private var showedMenu = false

    companion object {
        fun newInstance(): GameFragment = GameFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oldLayout = ConstraintSet()
        oldLayout.clone(view.fragment_game_layout)
        buttons = arrayListOf(
            view.main_r1_b1, view.main_r1_b2, view.main_r1_b3,
            view.main_r2_b1, view.main_r2_b2, view.main_r2_b3,
            view.main_r3_b1, view.main_r3_b2, view.main_r3_b3
        )
        prepareGame()
        setListeners(view)
    }

    fun prepareGame() {
        game = CGame()
        player1 = CPlayer("You", "")
        player2 = CPlayer("Enemy", "")
        val act = activity as MainActivity
        act.iPresenter.initGame(buttons, player1, player2, game, activity as MainActivity)
    }

    fun setListeners(view: View) {
        val act = activity as MainActivity
        view.main_cv_cl_tv1.setOnClickListener {
            act.iPresenter.initGame(buttons, player1, player2, game, activity as MainActivity)
        }
        view.main_cv_cl_tv2.setOnClickListener {
            act.setFragment("M")
        }
        buttons.forEach { button ->
            button.setOnClickListener { button2 ->
                act.iPresenter.onButtonClicked(
                    button2 as ImageView,
                    player1,
                    player2,
                    game,
                    buttons,
                    view.fragment_game_layout,
                    activity as MainActivity
                )
            }
        }
    }

}