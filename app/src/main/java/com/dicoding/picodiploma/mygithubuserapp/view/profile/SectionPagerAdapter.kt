package com.dicoding.picodiploma.mygithubuserapp.view.profile

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.mygithubuserapp.viewModel.ProfileViewModel

class SectionPagerAdapter(
    activity: AppCompatActivity,
    private val viewModel: ProfileViewModel
) :
    FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        if (position == 0) viewModel.getListUser()
        else viewModel.getListUser(false)

        return ListUserFragment(viewModel.listUser, viewModel.isLoadingTab)
    }
}