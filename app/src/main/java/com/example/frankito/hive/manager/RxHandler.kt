package com.example.frankito.hive.manager

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RxHandler {

    private var compositeDisposable = CompositeDisposable()

    fun clear() {
        compositeDisposable.clear()
    }

    fun addSubscription(disposable: Disposable){
        compositeDisposable.add(disposable)
    }

    fun <T> subscribe(flowable: Flowable<T>, data: ((input: T) -> Unit)? = null){
        compositeDisposable.add(flowable
                .subscribe {
                    data?.invoke(it)
                })
    }

    fun <T> subscribe(observable: Observable<T>, data: ((input: T) -> Unit)? = null) {
        compositeDisposable.add(observable.observeOn(AndroidSchedulers.mainThread(), false)
                .subscribeOn(Schedulers.io())
                .subscribe {
                    data?.invoke(it)
                })
    }

    fun <T> callAsync(observable: Observable<T>) {
        compositeDisposable.add(observable
                .subscribeOn(Schedulers.io())
                .subscribe())
    }
}