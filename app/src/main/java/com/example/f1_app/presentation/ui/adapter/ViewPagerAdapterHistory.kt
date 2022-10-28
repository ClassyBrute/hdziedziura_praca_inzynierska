package com.example.f1_app.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.f1_app.presentation.ui.HistoryDriversFragment
import com.example.f1_app.presentation.ui.HistoryFragment
import com.example.f1_app.presentation.ui.HistoryScheduleFragment
import com.example.f1_app.presentation.ui.HistoryTeamsFragment

class ViewPagerAdapterHistory(
    parentFragment: HistoryFragment, private var totalCount: Int
) : FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = totalCount
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HistoryDriversFragment()
            1 -> HistoryTeamsFragment()
            2 -> HistoryScheduleFragment()
            else -> HistoryDriversFragment()
        }
    }
}