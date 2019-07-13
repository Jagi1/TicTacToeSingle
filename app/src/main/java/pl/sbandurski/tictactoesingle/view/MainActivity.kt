package pl.sbandurski.tictactoesingle.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.synnapps.carouselview.ImageListener
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
        player1 = CPlayer("Player 1", "X")
        player2 = CPlayer("Player 2", "Y")
        setPresenter(CMainPresenter(this))
        main_iv.setOnClickListener {
            presenter.showMenu(main_cl as ConstraintLayout, showedMenu, oldLayout)
            showedMenu = !showedMenu
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
        main_cv_cl.pageCount = 3
        main_cv_cl.setImageListener(imageListener)
    }

    var imageListener = object: ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            when (position) {
                0 -> imageView?.setImageResource(R.drawable.easy)
                1 -> imageView?.setImageResource(R.drawable.medium)
                2 -> imageView?.setImageResource(R.drawable.hard)
            }
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun setPresenter(presenter: IMainContract.IPresenter) {
        this.presenter = presenter
    }
}