package com.rtugeek.android.colorseekbardemo.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class SectionsPagerAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> {
                AlphaSeekBarFragment.newInstance()
            }
            else -> {
                ColorSeekBarFragment.newInstance()
            }
        }
    }
}