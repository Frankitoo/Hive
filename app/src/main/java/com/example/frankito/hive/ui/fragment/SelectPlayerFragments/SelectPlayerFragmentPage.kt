package com.example.frankito.hive.ui.fragment.SelectPlayerFragments

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.frankito.hive.R
import com.example.frankito.hive.manager.PlayersManager
import com.example.frankito.hive.ui.adapter.PlayerSelectRecyclerAdapter
import com.example.frankito.hive.ui.base.BaseFragment
import com.example.frankito.hive.ui.dialog.showAddPlayerDialog
import com.example.frankito.hive.ui.fragment.SelectPlayerFragment
import kotlinx.android.synthetic.main.fragment_select_player_one.*

abstract class SelectPlayerFragmentPage : BaseFragment() {

    private var playerManager = PlayersManager.sharedInstance

    abstract fun playerSelected(id: Int? = null)

    override fun initUi() {

        val recycler = select_player_recycler
        recycler.layoutManager = LinearLayoutManager(context)

        val adapter = PlayerSelectRecyclerAdapter(context!!)

        playerManager.subscribeForPlayer {
            adapter.data = it
        }

        select_button.setOnClickListener {
            selectPlayer(adapter.selectedPlayerId)
        }

        recycler.adapter = adapter

        add_new_player_button.setOnClickListener {
            showAddPlayerDialog(context!!) { playerName ->
                playerManager.addPlayer(playerName)
            }
        }
    }

    private fun selectPlayer(id: Int? = null) {
        if (id == null) {
            val context = activity as AppCompatActivity
            Toast.makeText(context, getString(R.string.select_player), Toast.LENGTH_SHORT).show()
        } else {
            playerSelected(id)

            fragmentManager?.beginTransaction()?.remove(this)?.commit()

            val parentActivity = parentFragment as SelectPlayerFragment
            parentActivity.turnPage()
        }

    }
}