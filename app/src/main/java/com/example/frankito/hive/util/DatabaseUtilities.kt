package com.example.frankito.hive.util

import com.example.frankito.hive.model.Player
import io.realm.Realm

class DatabaseUtilities {

    private object Holder {
        val INSTANCE = DatabaseUtilities()
    }

    companion object {
        val instance: DatabaseUtilities by lazy { Holder.INSTANCE }
    }

    fun addPlayer(playerName: String? = null) {

        playerName ?: return

        Realm.getDefaultInstance().use {
            it.executeTransaction {
                // increment index
                val currentIdNum = it.where(Player::class.java).max("id")
                val nextId: Int
                if (currentIdNum == null) {
                    nextId = 1
                } else {
                    nextId = currentIdNum.toInt() + 1
                }

                val player = Player(nextId, playerName, 0)
                it.insertOrUpdate(player)
            }
        }
    }

    fun initPlayers(playerList: List<Player>?) {
        playerList ?: return

        Realm.getDefaultInstance().use {
            it.executeTransaction {
                it.where(Player::class.java).findAll().deleteAllFromRealm()
                it.insertOrUpdate(playerList)
            }
        }
    }

    fun playerWinnedUpdate(playerId: Int) {
        Realm.getDefaultInstance().use {
            it.executeTransaction {
                val player = it.where(Player::class.java).equalTo("id", playerId).findFirst()
                if (player != null) {
                    if(player.score != null) {
                        player.score = player.score!! + 100
                        it.copyToRealmOrUpdate(player)
                    }
                }
            }
        }
    }

}