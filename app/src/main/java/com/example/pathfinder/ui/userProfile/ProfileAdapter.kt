package com.example.pathfinder.ui.userProfile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ProfileAdapter(fm: FragmentManager, private val numberOfFragment: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MyWriteFragment()
            1 -> MyWriteFragment()
            else -> MyWriteFragment()
        }
    }

    override fun getCount(): Int {
        return numberOfFragment
    }
}