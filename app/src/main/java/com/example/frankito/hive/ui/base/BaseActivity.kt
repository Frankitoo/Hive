package com.example.frankito.hive.ui.base

import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.example.frankito.hive.manager.RxHandler
import com.example.frankito.hive.manager.UiManager
import com.example.frankito.hive.util.KeyboardUtilities
import io.reactivex.Observable

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getContentView(): Int

    abstract fun initUi()

    var rxHandler = RxHandler()
    val uiManager = UiManager.sharedInstance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        initUi()
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    override fun onDestroy() {
        super.onDestroy()
        rxHandler.clear()
        closeKeyboard()
    }

    protected fun <T> subscribe(observable: Observable<T>, data: ((input: T) -> Unit)? = null) {
        rxHandler.subscribe(observable, data)
    }

    protected open fun refreshData() {
        //override if needed
    }

    protected open fun closeKeyboard() {
        KeyboardUtilities.closeSoftKeyboardIfVisible(this)
    }

    protected fun showSnack(title: String?) {
        title?.let {
            Snackbar.make(findViewById(android.R.id.content), title, BaseTransientBottomBar.LENGTH_LONG).show()
        }
    }

}