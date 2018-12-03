package com.example.frankito.hive.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import com.example.frankito.hive.ui.fragment.SelectPlayerFragmentOne
import com.example.frankito.hive.ui.fragment.SelectPlayerFragmentTwo

internal class PlayerSelectPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val COUNT = 2

    var viewPager: ViewPager? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = SelectPlayerFragmentOne.newInstance()
            }
            1 -> {
                fragment = SelectPlayerFragmentTwo.newInstance()
            }
        }
        return fragment
    }
}