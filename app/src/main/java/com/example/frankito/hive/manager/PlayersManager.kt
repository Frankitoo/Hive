package com.example.frankito.hive.manager

import com.example.frankito.hive.model.Player

class PlayersManager private constructor() : BaseRealmManager<Player, Int>() {

    companion object {
        val sharedInstance = PlayersManager()
    }

    override fun getTypeClass() = Player::class.java

    fun addPlayer(playerName: String?) {
        playerName ?: return
        addElement(Player(0, playerName, 0))
    }

    fun subscribeForPlayer(listener: ((List<Player>) -> Unit)) {
        subscribeToObjects(listener)
    }

    fun deletePlayer(id: Int) {
        val obs = getRealm().executeTransaction {
            getBaseQuery(it).equalTo("id", id).findFirst()?.deleteFromRealm()
        }
    }

    fun deletePlayers() {
        getRealm().executeTransaction {
            getBaseQuery(it).findAll()?.deleteAllFromRealm()
        }
    }

}