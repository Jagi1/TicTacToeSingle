package pl.sbandurski.tictactoesingle.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.*
import pl.sbandurski.tictactoesingle.R

class SettingsFragment: Fragment() {

    companion object {
        fun newInstance(): SettingsFragment = SettingsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as MainActivity
        when (activity.difficulty) {
            1 -> {
                difficulty_button1.setImageResource(R.drawable.shape_button1_light)
                difficulty_button1_text.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            }
            2 -> {
                difficulty_button2.setImageResource(R.drawable.shape_button2_light)
                difficulty_button2_text.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            }
            3 -> {
                difficulty_button3.setImageResource(R.drawable.shape_button3_light)
                difficulty_button3_text.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            }
        }
        difficulty_button1.setOnClickListener {
            difficulty_button1.setImageResource(R.drawable.shape_button1_light)
            difficulty_button2.setImageResource(R.drawable.shape_button2)
            difficulty_button3.setImageResource(R.drawable.shape_button3)
            difficulty_button1_text.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            difficulty_button2_text.setTextColor(resources.getColor(R.color.colorText))
            difficulty_button3_text.setTextColor(resources.getColor(R.color.colorText))
            activity.difficulty = 1
        }
        difficulty_button2.setOnClickListener {
            difficulty_button1.setImageResource(R.drawable.shape_button1)
            difficulty_button2.setImageResource(R.drawable.shape_button2_light)
            difficulty_button3.setImageResource(R.drawable.shape_button3)
            difficulty_button1_text.setTextColor(resources.getColor(R.color.colorText))
            difficulty_button2_text.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            difficulty_button3_text.setTextColor(resources.getColor(R.color.colorText))
            activity.difficulty = 2
        }
        difficulty_button3.setOnClickListener {
            difficulty_button1.setImageResource(R.drawable.shape_button1)
            difficulty_button2.setImageResource(R.drawable.shape_button2)
            difficulty_button3.setImageResource(R.drawable.shape_button3_light)
            difficulty_button1_text.setTextColor(resources.getColor(R.color.colorText))
            difficulty_button2_text.setTextColor(resources.getColor(R.color.colorText))
            difficulty_button3_text.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            activity.difficulty = 3
        }
        settings_back_button.setOnClickListener {
            activity.setFragment("M")
        }
    }
}