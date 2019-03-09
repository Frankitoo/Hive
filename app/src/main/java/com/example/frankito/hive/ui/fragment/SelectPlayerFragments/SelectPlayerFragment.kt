package com.example.frankito.hive.ui.fragment.SelectPlayerFragments

import android.support.v4.view.ViewPager
import com.example.frankito.hive.R
import com.example.frankito.hive.ui.activity.MainActivity
import com.example.frankito.hive.ui.adapter.PlayerSelectPagerAdapter
import com.example.frankito.hive.ui.base.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_select_player.*

class SelectPlayerFragment : BaseDialogFragment() {

    private lateinit var viewPager: ViewPager

    override fun getLayoutRes() = R.layout.fragment_select_player

    override fun initUi() {
        viewPager = select_player_pager

        val pagerAdapter = PlayerSelectPagerAdapter(childFragmentManager)
        pagerAdapter.viewPager = viewPager
        viewPager.adapter = pagerAdapter
    }

    companion object {
        fun newInstance(): SelectPlayerFragment {
            return SelectPlayerFragment()
        }
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

