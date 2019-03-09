package com.example.frankito.hive.manager

import com.example.frankito.hive.model.BaseRealmObject
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery

abstract class BaseRealmManager<REALM_OBJECT, ID_TYPE> :
        BaseManager() where REALM_OBJECT : RealmObject, REALM_OBJECT : BaseRealmObject<Int> {

    protected abstract fun getTypeClass(): Class<REALM_OBJECT>

    private var realm: Realm? = null

    fun getRealm(): Realm {
        if (realm == null || realm!!.isClosed) {
            realm = Realm.getDefaultInstance()
        }
        return realm!!
    }

    private fun close() {
        realm?.close()
    }

    protected fun subscribeToObjects(rxHandler: RxHandler, listener: ((List<REALM_OBJECT>) -> Unit)) {
        rxHandler.subscribe(
                Realm.getDefaultInstance()
                        .where(getTypeClass())
                        .findAllAsync()
                        .asFlowable(), listener)
    }

    protected fun addElement(rxHandler: RxHandler, element: REALM_OBJECT?) {
        element ?: return
        rxHandler.callAsync(
                Observable.fromCallable {
                    Realm.getDefaultInstance().executeTransaction {
                        val currentIdNum = it.where(getTypeClass()).max("id")
                        val nextId: Int
                        if (currentIdNum == null) {
                            nextId = 1
                        } else {
                            nextId = currentIdNum.toInt() + 1
                        }
                        element.id = nextId
                        it.insertOrUpdate(element)
                    }
                })
    }

    fun getObjectById(id: ID_TYPE?, realm: Realm = getRealm()): REALM_OBJECT? {
        id ?: return null

        if (id is String) {
            return getBaseQuery(realm).equalTo("id", id).findFirst()
        } else if (id is Int) {
            return getBaseQuery(realm).equalTo("id", id).findFirst()
        }
        return null
    }

    internal open fun getBaseQuery(realm: Realm = getRealm()): RealmQuery<REALM_OBJECT> {
        return realm.where(getTypeClass())
    }

}