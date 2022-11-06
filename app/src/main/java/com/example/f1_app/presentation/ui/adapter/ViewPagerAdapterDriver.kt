package com.example.f1_app.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.f1_app.presentation.ui.DriverDetailsFragment
import com.example.f1_app.presentation.ui.DriverDetailsInformationFragment
import com.example.f1_app.presentation.ui.DriverDetailsResultsFragment

class ViewPagerAdapterDriver(
parentFragment: DriverDetailsFragment, private var totalCount: Int
) : FragmentStateAdapter(parentFragment) {
    override fun getItemCount(): Int = totalCount
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DriverDetailsInformationFragment()
            1 -> DriverDetailsResultsFragment()
            else -> DriverDetailsInformationFragment()
        }
    }
}