package com.example.frankito.hive.util

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import com.example.frankito.hive.R
import com.example.frankito.hive.model.Player
import com.example.frankito.hive.ui.activity.GameActivity
import com.example.frankito.hive.ui.dialog.showPlayerWonDialog
import io.realm.Realm

object HelperUtilities {

    fun getPlayerOneId(context: Context): Int {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(Constants.SELECTED_PLAYER_ONE, 0)
    }

    fun storePlayerOneId(context: Context, playerOneId: Int?) {
        playerOneId ?: return
        val prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(Constants.SELECTED_PLAYER_ONE, playerOneId).commit()
    }

    fun getPlayerTwoId(context: Context): Int {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getInt(Constants.SELECTED_PLAYER_TWO, 0)
    }

    fun storePlayerTwoId(context: Context, playerTwoId: Int?) {
        playerTwoId ?: return
        val prefs = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(Constants.SELECTED_PLAYER_TWO, playerTwoId).commit()
    }

    fun playerOneHasWon(context: Context) {
        val playerId = getPlayerOneId(context)
        playerHasWon(context, playerId)
    }

    fun playerTwoHasWon(context: Context) {
        val playerId = getPlayerTwoId(context)
        playerHasWon(context, playerId)
    }

    private fun playerHasWon(context: Context, playerId: Int) {

        DatabaseUtilities.instance.playerWinnedUpdate(playerId)

        var player: Player? = null
        Realm.getDefaultInstance().use {
            player = it.where(Player::class.java).equalTo("id", playerId).findFirst()
        }

        showPlayerWonDialog(player?.name!!, context)

    }
}