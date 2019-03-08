package com.example.frankito.hive.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import com.example.frankito.hive.R
import com.example.frankito.hive.model.Player

class PlayerSelectRecyclerAdapter(private val context: Context) : RecyclerView.Adapter<PlayerSelectRecyclerAdapter.ViewHolder>() {

    var checkboxList = ArrayList<CheckBox>()
    var selectedPlayerId: Int? = null

    var data: List<Player>? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var playerName: TextView = itemView.findViewById(R.id.item_player_name)
        internal var itemLayout: LinearLayout = itemView.findViewById(R.id.item_player_layout)
        internal var itemCheckBox: CheckBox = itemView.findViewById(R.id.item_player_checkbox)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerSelectRecyclerAdapter.ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_player, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data?.get(position)

        holder.playerName.text = item?.name

        holder.itemLayout.isClickable = true

        checkboxList.add(holder.itemCheckBox)

        holder.itemCheckBox.isClickable = false

        holder.itemLayout.setOnClickListener {
            setCheckBoxes(position)
            selectedPlayerId = item?.id
            //HelperUtilities.storeBeerId(context, selectedBeerId)
        }

    }

    private fun setCheckBoxes(position: Int) {
        for (it in checkboxList.indices) {
            checkboxList[it].isChecked = (it == position)
        }
    }

}