package com.dicoding.picodiploma.mygithubuserapp.view.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.mygithubuserapp.R
import com.dicoding.picodiploma.mygithubuserapp.databinding.ActivityDetailPageBinding
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.BaseActivity
import com.dicoding.picodiploma.mygithubuserapp.viewModel.ProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailPageBinding
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        viewModel.user.value = user
        Log.e("TAG", "INPUTTED USER")
        viewModel.getUserDetail()
        viewModel.getListUser()
        viewModel.user.observe(this) { setUserData(it) }
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.snackBarText.observe(this) { showSnackBar(it) }
        val sectionPagerAdapter = SectionPagerAdapter(this, viewModel)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.e("TAG", "on page selected $position")
                if (position == 0) viewModel.getListUser()
                else viewModel.getListUser(false)
            }
        })

//        viewPager.addOnLayoutChangeListener(object : ViewPager.OnPageChangeListener,
//            View.OnLayoutChangeListener {
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//                Log.e("TAG", "on layout change $position")
//            }
//
//            override fun onPageSelected(position: Int) {

//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//                Log.e("TAG", "on page scroll state $state")
//            }
//
//            override fun onLayoutChange(
//                v: View?,
//                left: Int,
//                top: Int,
//                right: Int,
//                bottom: Int,
//                oldLeft: Int,
//                oldTop: Int,
//                oldRight: Int,
//                oldBottom: Int
//            ) {
//            }
//        })

        TabLayoutMediator(binding.tabLayoutDetail, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.detailPageProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(user: User) {
        supportActionBar?.title = user.name
        Glide.with(this).load(user.avatarUrl).into(binding.imageViewUser)

        binding.textViewFollowersFollowing.text =
            StringBuilder("${user.followers} Followers & ${user.following} Following")

        binding.textViewName.text = user.name
        binding.textViewRepositories.text = StringBuilder("${user.publicRepos ?: 0} repositories")
        binding.textViewUsername.text = StringBuilder("@${user.login}")
        binding.layoutMoreDetail.textViewUserCompany.text = user.company
        binding.layoutMoreDetail.textViewUserLocation.text = user.location
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
    }
}