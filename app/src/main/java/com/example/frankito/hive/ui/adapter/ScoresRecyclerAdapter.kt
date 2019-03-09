package com.example.frankito.hive.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.frankito.hive.R
import com.example.frankito.hive.model.Player

class ScoresRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<ScoresRecyclerAdapter.ViewHolder>() {

    var data: List<Player>? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var playerNameText: TextView = itemView.findViewById(R.id.item_player_name_text)
        internal var scoreText : TextView = itemView.findViewById(R.id.item_score_text)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresRecyclerAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_scores, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data?.get(position)

        holder.playerNameText.text = item?.name

        val scoreText = item?.score.toString() + " pont"

        holder.scoreText.text = scoreText

    }

}