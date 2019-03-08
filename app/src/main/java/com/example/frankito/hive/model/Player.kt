package com.example.frankito.hive.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Player(
        @PrimaryKey
        override var id: Int = 0,
        var name: String? = null,
        var score: Int? = null

) : RealmObject(), BaseRealmObject<Int>