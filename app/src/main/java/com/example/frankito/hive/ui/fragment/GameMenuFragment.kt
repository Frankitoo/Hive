package com.example.frankito.hive.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.frankito.hive.R
import com.example.frankito.hive.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_game_menu.*

class GameMenuFragment : DialogFragment() {

    companion object {
        fun newInstance(): GameMenuFragment {
            return GameMenuFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_menu, container, false)
    }

    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = activity as AppCompatActivity

        menu_restart_button.setOnClickListener {
            callRestart()
        }
        menu_end_game_button.setOnClickListener {
            callEndGame()
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
