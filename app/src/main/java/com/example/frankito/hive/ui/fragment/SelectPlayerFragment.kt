package com.example.frankito.hive.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.frankito.hive.R
import com.example.frankito.hive.ui.activity.MainActivity
import com.example.frankito.hive.ui.adapter.PlayerSelectPagerAdapter

class SelectPlayerFragment : DialogFragment() {

    companion object {
        fun newInstance(): SelectPlayerFragment {
            return SelectPlayerFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_select_player, container, false)
    }

    private lateinit var mContext: Context
    private lateinit var viewPager: ViewPager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mContext = activity as MainActivity

        viewPager = view.findViewById<ViewPager>(R.id.select_player_pager)

        val pagerAdapter = PlayerSelectPagerAdapter(childFragmentManager)
        pagerAdapter.viewPager = viewPager
        viewPager.adapter = pagerAdapter

    }

    fun turnPage() {
        val currentItem = viewPager.currentItem
        if (currentItem != viewPager.adapter?.count?.minus(1)) {
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        } else {
            callFinish()
        }
    }

    private fun callFinish() {
        fragmentManager?.beginTransaction()?.remove(this)?.commit()

        val parentActivity = activity as MainActivity
        parentActivity.startGame()

    }

}

