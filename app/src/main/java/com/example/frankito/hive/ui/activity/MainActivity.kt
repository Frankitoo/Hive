package com.example.frankito.hive.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.frankito.hive.R
import com.example.frankito.hive.manager.GameManager
import com.example.frankito.hive.ui.view.HexaElement
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        play_button.setOnClickListener {
            GameManager.currentPlayer = HexaElement.WhichPlayer.PLAYERONE
            val intent = Intent(this@MainActivity,GameActivity::class.java)
            startActivity(intent)
        }
        quit_button.setOnClickListener {
            finish()
        }

    }
}
