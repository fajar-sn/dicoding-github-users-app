package com.dicoding.picodiploma.mygithubuserapp.view.profile

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.mygithubuserapp.R
import com.dicoding.picodiploma.mygithubuserapp.databinding.ActivityDetailPageBinding
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.BaseActivity
import com.dicoding.picodiploma.mygithubuserapp.view.adapter.SectionPagerAdapter
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
        viewModel.getUserDetail()
        viewModel.user.observe(this) { setUserData(it) }
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.snackBarText.observe(this) { showSnackBar(it) }
        attachTabLayout(user)
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

    private fun attachTabLayout(user: User) {
        val sectionPagerAdapter = SectionPagerAdapter(this, user)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(binding.tabLayoutDetail, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
    }
}