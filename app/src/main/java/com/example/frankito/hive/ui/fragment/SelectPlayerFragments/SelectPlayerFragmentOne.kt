package com.example.frankito.hive.ui.fragment.SelectPlayerFragments

import com.example.frankito.hive.R
import com.example.frankito.hive.util.HelperUtilities

class SelectPlayerFragmentOne : SelectPlayerFragmentPage() {

    companion object {
        fun newInstance(): SelectPlayerFragmentOne {
            return SelectPlayerFragmentOne()
        }
    }

    override fun getLayoutRes() = R.layout.fragment_select_player_one

    override fun playerSelected(id: Int?) {
        HelperUtilities.storePlayerOneId(context!!, id)
    }
}