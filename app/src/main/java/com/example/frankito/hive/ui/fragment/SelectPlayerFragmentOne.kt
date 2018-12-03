package com.example.frankito.hive.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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
import com.example.frankito.hive.util.HelperUtilities
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_select_player_one.*

class SelectPlayerFragmentOne : Fragment() {

    companion object {
        fun newInstance(): SelectPlayerFragmentOne {
            return SelectPlayerFragmentOne()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_player_one, container, false)
    }

    private lateinit var realm: Realm
    private lateinit var mContext: Context

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = activity as MainActivity
        realm = Realm.getDefaultInstance()

        val players = realm.where(Player::class.java).findAll()

        val recycler = select_player_recycler
        recycler.layoutManager = LinearLayoutManager(mContext)
        val adapter = PlayerSelectRecyclerAdapter(players, mContext)
        recycler.adapter = adapter

        select_button.setOnClickListener {
            selectPlayer(adapter.selectedPlayerId)
        }

        add_new_player_button.setOnClickListener {
            showAddPlayerDialog(mContext) { playerName ->
                DatabaseUtilities.instance.addPlayer(playerName)
            }
        }

    }

    private fun selectPlayer(id: Int? = null) {
        if (id == null) {
            val context = activity as AppCompatActivity
            Snackbar.make(context.currentFocus, getString(R.string.select_player), BaseTransientBottomBar.LENGTH_SHORT).show()
        } else {
            playerOneSelected(id)

            fragmentManager?.beginTransaction()?.remove(this)?.commit()

            val parentActivity = parentFragment as SelectPlayerFragment
            parentActivity.turnPage()
        }

    }

    private fun playerOneSelected(id: Int? = null) {
        HelperUtilities.storePlayerOneId(mContext, id)
    }

    override fun onDestroy() {
        realm.close()
        super.onDestroy()
    }
}