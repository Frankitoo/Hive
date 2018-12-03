package com.example.frankito.hive.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.frankito.hive.R
import com.example.frankito.hive.model.Player
import com.example.frankito.hive.ui.activity.MainActivity
import com.example.frankito.hive.ui.adapter.PlayerSelectRecyclerAdapter
import com.example.frankito.hive.ui.dialog.showAddPlayerDialog
import com.example.frankito.hive.util.DatabaseUtilities
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_select_player_two.*

class SelectPlayerFragmentTwo : Fragment() {

    companion object {
        fun newInstance(): SelectPlayerFragmentTwo {
            return SelectPlayerFragmentTwo()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_player_two, container, false)
    }

    private lateinit var realm: Realm
    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = activity as MainActivity
        realm = Realm.getDefaultInstance()

        val players = realm.where(Player::class.java).findAll()

        select_player_recycler.layoutManager = LinearLayoutManager(mContext)
        val adapter = PlayerSelectRecyclerAdapter(players,mContext)
        select_player_recycler.adapter = adapter

        select_button.setOnClickListener {
            selectPlayer()
        }

        add_new_player_button.setOnClickListener {
            showAddPlayerDialog(mContext){playerName ->
                 DatabaseUtilities.instance.addPlayer(playerName)
            }
        }

    }

    private fun selectPlayer() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
        val parentActivity = parentFragment as SelectPlayerFragment
        parentActivity.turnPage()

    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}