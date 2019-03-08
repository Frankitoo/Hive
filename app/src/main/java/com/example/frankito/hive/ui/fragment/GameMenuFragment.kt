package com.example.frankito.hive.ui.fragment

import android.support.v7.app.AppCompatActivity
import com.example.frankito.hive.R
import com.example.frankito.hive.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_game_menu.*

class GameMenuFragment : BaseDialogFragment() {

    override fun getLayoutRes() = R.layout.fragment_game_menu

    override fun initUi() {
        menu_restart_button.setOnClickListener {
            callRestart()
        }
        menu_end_game_button.setOnClickListener {
            callEndGame()
        }
    }

    companion object {
        fun newInstance(): GameMenuFragment {
            return GameMenuFragment()
        }
    }

    private fun callRestart(){
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
        val parentActivity = activity as AppCompatActivity
        parentActivity.recreate()
    }

    private fun callEndGame() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
        val parentActivity = activity as AppCompatActivity
        parentActivity.finish()
    }

}
