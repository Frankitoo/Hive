package com.example.frankito.hive.ui.fragment.SelectPlayerFragments

import com.example.frankito.hive.R
import com.example.frankito.hive.util.HelperUtilities

class SelectPlayerFragmentTwo : SelectPlayerFragmentPage() {

    companion object {
        fun newInstance(): SelectPlayerFragmentTwo {
            return SelectPlayerFragmentTwo()
        }
    }

    override fun getLayoutRes() = R.layout.fragment_select_player_two

    override fun playerSelected(id: Int?) {
        HelperUtilities.storePlayerTwoId(context!!, id)
    }
}