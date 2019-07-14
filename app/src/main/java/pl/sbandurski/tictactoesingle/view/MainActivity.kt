package pl.sbandurski.tictactoesingle.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import pl.sbandurski.tictactoesingle.R
import pl.sbandurski.tictactoesingle.model.CGame
import pl.sbandurski.tictactoesingle.model.CPlayer
import pl.sbandurski.tictactoesingle.presenter.CMainPresenter
import pl.sbandurski.tictactoesingle.presenter.IMainContract

class MainActivity : AppCompatActivity(), IMainContract.IView {

    private lateinit var presenter: IMainContract.IPresenter
    private lateinit var player1: CPlayer
    private lateinit var player2: CPlayer
    private lateinit var game: CGame
    private lateinit var buttons: ArrayList<Button>
    private lateinit var oldLayout: ConstraintSet
    private var showedMenu = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        oldLayout = ConstraintSet()
        oldLayout.clone(main_cl)
        buttons = arrayListOf(
            main_r1_b1, main_r1_b2, main_r1_b3,
            main_r2_b1, main_r2_b2, main_r2_b3,
            main_r3_b1, main_r3_b2, main_r3_b3
        )
        game = CGame()
        player1 = CPlayer("You", "")
        player2 = CPlayer("Enemy", "")
        setPresenter(CMainPresenter(this))
        main_iv.setOnClickListener {
            presenter.showMenu(main_cl as ConstraintLayout, showedMenu, oldLayout)
            showedMenu = !showedMenu
        }
        main_cv_cl_tv1.setOnClickListener {
            presenter.initGame(buttons, player1, player2, game)
        }
        main_cv_cl_tv2.setOnClickListener {
            finishAndRemoveTask()
        }
        presenter.initGame(buttons, player1, player2, game)
        buttons.forEach { button ->
            button.setOnClickListener { view ->
                presenter.onButtonClicked(
                    view as Button,
                    player1,
                    player2,
                    game,
                    buttons
                )
            }
        }
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setPresenter(presenter: IMainContract.IPresenter) {
        this.presenter = presenter
    }
}