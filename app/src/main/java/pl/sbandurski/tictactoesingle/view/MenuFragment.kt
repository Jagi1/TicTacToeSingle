package pl.sbandurski.tictactoesingle.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_menu.view.*
import pl.sbandurski.tictactoesingle.R
import pl.sbandurski.tictactoesingle.presenter.IMainContract

class MenuFragment: Fragment() {

    companion object {
        fun newInstance(): MenuFragment = MenuFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_menu, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners(view)
    }

    private fun setListeners(view: View) = with(view) {
        val act = activity as MainActivity
        menu_button_1.setOnClickListener {
            act.setFragment("G")
        }
        menu_button_2.setOnClickListener {
            act.setFragment("S")
        }
        menu_button_3.setOnClickListener {
            activity?.finishAndRemoveTask()
        }
    }
}