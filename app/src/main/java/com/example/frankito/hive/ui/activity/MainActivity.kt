package com.example.frankito.hive.ui.activity

import com.example.frankito.hive.R
import com.example.frankito.hive.manager.GameManager
import com.example.frankito.hive.ui.base.BaseActivity
import com.example.frankito.hive.ui.fragment.SelectPlayerFragments.SelectPlayerFragment
import com.example.frankito.hive.ui.view.HexaElement
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun getContentView() = R.layout.activity_main

    override fun initUi() {

        play_button.setOnClickListener {
            val fm = supportFragmentManager
            val selectPlayerFragment = SelectPlayerFragment.newInstance()
            selectPlayerFragment.show(fm, "fragment_select_player")
        }

        score_button.setOnClickListener {
            //initPlayers()
            startScoresActivity(this)
        }

        quit_button.setOnClickListener {
            finish()
        }
    }

    fun startGame() {
        GameManager.currentPlayer = HexaElement.WhichPlayer.PLAYERONE
        startGameActivity(this)
    }
}
