package com.example.frankito.hive.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.frankito.hive.R
import com.example.frankito.hive.model.Player
import com.example.frankito.hive.ui.adapter.ScoresRecyclerAdapter
import io.realm.Realm

class ScoresActivity : AppCompatActivity() {

    inner class ViewHolder {
        internal val scoresRecycler = findViewById<RecyclerView>(R.id.scores_recycler)
    }

    private lateinit var viewHolder: ViewHolder
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        viewHolder = ViewHolder()
        realm = Realm.getDefaultInstance()

        initScoresRecycler()
    }

    private fun initScoresRecycler() {
        val players = realm.where(Player::class.java).findAll() as List<Player>

        val sortedPlayers = players.sortedByDescending { it.score }
        viewHolder.scoresRecycler.layoutManager = LinearLayoutManager(this@ScoresActivity)
        val adapter = ScoresRecyclerAdapter(sortedPlayers, this@ScoresActivity)
        viewHolder.scoresRecycler.adapter = adapter

    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}
