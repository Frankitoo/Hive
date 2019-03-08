package com.example.frankito.hive.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.example.frankito.hive.R
import com.example.frankito.hive.model.Player
import com.example.frankito.hive.ui.adapter.ScoresRecyclerAdapter
import com.example.frankito.hive.ui.base.BaseActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_scores.*

fun startScoresActivity(activity: BaseActivity) {
    val intent = Intent(activity, ScoresActivity::class.java)
    activity.startActivity(intent)
}

class ScoresActivity : BaseActivity() {

    private lateinit var realm: Realm

    override fun getContentView() = R.layout.activity_scores

    override fun initUi() {
        realm = Realm.getDefaultInstance()
        initScoresRecycler()
    }

    //TODO database call
    private fun initScoresRecycler() {
        val players = realm.where(Player::class.java).findAll() as List<Player>

        val sortedPlayers = players.sortedByDescending { it.score }
        scores_recycler.layoutManager = LinearLayoutManager(this@ScoresActivity)

        val adapter = ScoresRecyclerAdapter(sortedPlayers, this@ScoresActivity)
        scores_recycler.adapter = adapter

    }

    //TODO database close
    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}
