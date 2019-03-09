package com.example.frankito.hive.ui.fragment.SelectPlayerFragments

import com.example.frankito.hive.R
import com.example.frankito.hive.manager.PlayersManager
import kotlinx.android.synthetic.main.fragment_select_player_page.*

class SelectPlayerFragmentTwo : SelectPlayerFragmentPage() {

    companion object {
        fun newInstance(): SelectPlayerFragmentTwo {
            return SelectPlayerFragmentTwo()
        }
    }

    override fun getLayoutRes() = R.layout.fragment_select_player_page

    override fun initUi() {
        super.initUi()
        select_button.text = getString(R.string.start)
    }

    override fun playerSelected(id: Int?) {
        PlayersManager.sharedInstance.storePlayerTwoId(context!!, id)
    }
}