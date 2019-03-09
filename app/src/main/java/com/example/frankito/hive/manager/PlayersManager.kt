package com.example.frankito.hive.manager

import android.content.Context
import com.example.frankito.hive.model.Player
import com.example.frankito.hive.ui.dialog.showPlayerWonDialog
import com.example.frankito.hive.util.Constants
import io.realm.Realm

class PlayersManager private constructor() : BaseRealmManager<Player, Int>() {

    companion object {
        val sharedInstance = PlayersManager()
    }

    override fun getTypeClass() = Player::class.java

    fun addPlayer(rxHandler: RxHandler, playerName: String?) {
        playerName ?: return
        addElement(rxHandler, Player(0, playerName, 0))
    }

    fun subscribeForPlayer(rxHandler: RxHandler, listener: ((List<Player>) -> Unit)) {
        subscribeToObjects(rxHandler, listener)
    }

    fun storePlayerOneId(context: Context, playerOneId: Int?) {
        playerOneId ?: return
        getSharedPreferences(context).edit().putInt(Constants.SELECTED_PLAYER_ONE, playerOneId).apply()
    }

    fun storePlayerTwoId(context: Context, playerTwoId: Int?) {
        playerTwoId ?: return
        getSharedPreferences(context).edit().putInt(Constants.SELECTED_PLAYER_TWO, playerTwoId).apply()
    }

    fun playerOneHasWon(context: Context) {
        val playerId = getPlayerOneId(context)
        playerHasWon(context, playerId)
    }

    fun playerTwoHasWon(context: Context) {
        val playerId = getPlayerTwoId(context)
        playerHasWon(context, playerId)
    }

    fun deletePlayer(id: Int) {
        getRealm().executeTransaction {
            getBaseQuery(it).equalTo("id", id).findFirst()?.deleteFromRealm()
        }
    }

    fun deletePlayers() {
        getRealm().executeTransaction {
            getBaseQuery(it).findAll()?.deleteAllFromRealm()
        }
    }

    private fun getPlayerOneId(context: Context): Int {
        return getSharedPreferences(context).getInt(Constants.SELECTED_PLAYER_ONE, 0)
    }

    private fun getPlayerTwoId(context: Context): Int {
        return getSharedPreferences(context).getInt(Constants.SELECTED_PLAYER_TWO, 0)
    }

    private fun playerHasWon(context: Context, playerId: Int) {

        var player : Player? = null

        Realm.getDefaultInstance().executeTransaction {
            player = getObjectById(playerId, it)
            if (player != null) {
                if (player!!.score != null) {
                    player!!.score = player!!.score!! + 100
                    it.copyToRealmOrUpdate(player!!)
                }
            }
        }
        showPlayerWonDialog(player?.name!!, context)
    }


}