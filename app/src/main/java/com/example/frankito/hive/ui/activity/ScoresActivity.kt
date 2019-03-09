package com.example.frankito.hive.ui.activity

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import com.example.frankito.hive.R
import com.example.frankito.hive.manager.PlayersManager
import com.example.frankito.hive.ui.adapter.ScoresRecyclerAdapter
import com.example.frankito.hive.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_scores.*

fun startScoresActivity(activity: BaseActivity) {
    val intent = Intent(activity, ScoresActivity::class.java)
    activity.startActivity(intent)
}

class ScoresActivity : BaseActivity() {

    var playerManager = PlayersManager.sharedInstance

    override fun getContentView() = R.layout.activity_scores

    override fun initUi() {
        initScoresRecycler()
    }

    private fun initScoresRecycler() {

        scores_recycler.layoutManager = LinearLayoutManager(this)
        val adapter = ScoresRecyclerAdapter(this)
        scores_recycler.adapter = adapter

        playerManager.subscribeForPlayer(rxHandler) {
            val sortedPlayers = it.sortedByDescending { it.score }
            adapter.data = sortedPlayers
        }

    }
}
