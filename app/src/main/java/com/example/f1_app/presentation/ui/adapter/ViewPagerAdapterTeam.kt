package com.example.f1_app.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.f1_app.presentation.ui.TeamDetailsFragment
import com.example.f1_app.presentation.ui.TeamDetailsInformationFragment
import com.example.f1_app.presentation.ui.TeamDetailsResultsFragment

class ViewPagerAdapterTeam(
    parentFragment: TeamDetailsFragment, private var totalCount: Int
) : FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = totalCount
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> TeamDetailsInformationFragment()
            1 -> TeamDetailsResultsFragment()
            else -> TeamDetailsInformationFragment()
        }
    }
}