package com.dicoding.picodiploma.mygithubuserapp.view.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.profile.FollowersFragment
import com.dicoding.picodiploma.mygithubuserapp.view.profile.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val user: User) :
    FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val bundle = Bundle()
        var fragment: Fragment? = null

        when (position) {
            0 -> {
                bundle.putParcelable("extra_user", user)
                fragment = FollowersFragment()
            }
            1 -> {
                bundle.putParcelable("extra_user", user)
                fragment = FollowingFragment()
            }
        }

        fragment?.arguments = bundle
        return fragment as Fragment
    }
}