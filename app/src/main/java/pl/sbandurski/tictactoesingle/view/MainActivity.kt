package pl.sbandurski.tictactoesingle.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.view.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_menu.view.*
import pl.sbandurski.tictactoesingle.R
import pl.sbandurski.tictactoesingle.presenter.CMainPresenter
import pl.sbandurski.tictactoesingle.presenter.IMainContract

class MainActivity : AppCompatActivity(), IMainContract.IView {

    lateinit var iPresenter: IMainContract.IPresenter
    var difficulty = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setPresenter(CMainPresenter(this))
        val fragment = MenuFragment.newInstance()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment, fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroy() {
        iPresenter.onDestroy()
        super.onDestroy()
    }

    fun setFragment(fragmentType: String) {
        when (fragmentType) {
            "M" -> {
                val fragment = MenuFragment.newInstance()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                fragmentTransaction.replace(R.id.fragment, fragment)
                fragmentTransaction.commit()
            }
            "S" -> {
                val fragment = SettingsFragment.newInstance()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                fragmentTransaction.replace(R.id.fragment, fragment)
                fragmentTransaction.commit()
            }
            else -> {
                val fragment = GameFragment.newInstance()
                val fragmentManager = supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                fragmentTransaction.replace(R.id.fragment, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    override fun setPresenter(presenter: IMainContract.IPresenter) {
        this.iPresenter = presenter
    }
}