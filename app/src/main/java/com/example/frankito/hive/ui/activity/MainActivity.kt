package com.example.frankito.hive.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.frankito.hive.R
import com.example.frankito.hive.manager.GameManager
import com.example.frankito.hive.model.Player
import com.example.frankito.hive.ui.fragment.SelectPlayerFragment
import com.example.frankito.hive.ui.view.HexaElement
import com.example.frankito.hive.util.DatabaseUtilities
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        play_button.setOnClickListener {
            val fm = supportFragmentManager
            val selectPlayerFragment = SelectPlayerFragment.newInstance()
            selectPlayerFragment.show(fm ,"fragment_select_player")
        }

        score_button.setOnClickListener {
            initPlayers()

            val intent = Intent(this@MainActivity, ScoresActivity::class.java)
            startActivity(intent)
        }

        quit_button.setOnClickListener {
            finish()
        }

    }

    fun startGame(){
        GameManager.currentPlayer = HexaElement.WhichPlayer.PLAYERONE
        val intent = Intent(this@MainActivity, GameActivity::class.java)
        startActivity(intent)
    }

    private fun initPlayers() {
        val players = ArrayList<Player>()
        players.add(Player(0, "jozsi", 100))
        players.add(Player(1, "kati", 200))
        players.add(Player(2, "pali", 300))
        players.add(Player(3, "hali", 400))
        players.add(Player(4, "vmi", 500))

        DatabaseUtilities.instance.initPlayers(players)
    }

    private fun playerOneSelected(id: Int){

    }

    private fun playerTwoSelected(id: Int){

    }
}
