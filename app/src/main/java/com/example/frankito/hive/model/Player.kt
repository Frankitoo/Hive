package com.example.frankito.hive.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Player (

        @PrimaryKey
        var id: Int? = null,
        var name: String? = null,
        var score : Int? = null

) : RealmObject()