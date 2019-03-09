package com.example.frankito.hive.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.frankito.hive.manager.RxHandler
import com.example.frankito.hive.manager.UiManager
import io.reactivex.Observable

abstract class BaseFragment : Fragment() {

    private lateinit var mainView: View

    protected abstract fun getLayoutRes(): Int

    var rxHandler = RxHandler()
    private var uiManager = UiManager.sharedInstance

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater.inflate(getLayoutRes(), container, false)
        mainView.setOnTouchListener { _, _ -> true }
        return mainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    protected open fun initUi() {
        //override if needed
    }

    protected open fun refreshData() {
        //override if needed
    }

    override fun onDetach() {
        super.onDetach()
        rxHandler.clear()
    }

    protected fun <T> subscribe(observable: Observable<T>, data: ((input: T) -> Unit)? = null) {
        rxHandler.subscribe(observable, data)
    }

}