package com.example.f1_app.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.f1_app.presentation.ui.ScheduleFragment
import com.example.f1_app.presentation.ui.SchedulePastFragment
import com.example.f1_app.presentation.ui.ScheduleUpcomingFragment

class ViewPagerAdapter(
    parentFragment: ScheduleFragment, private var totalCount: Int
) : FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = totalCount
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ScheduleUpcomingFragment()
            1 -> SchedulePastFragment()
            else -> ScheduleUpcomingFragment()
        }
    }
}