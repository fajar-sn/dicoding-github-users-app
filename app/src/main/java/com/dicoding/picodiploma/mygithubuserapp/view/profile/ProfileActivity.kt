package com.dicoding.picodiploma.mygithubuserapp.view.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.mygithubuserapp.R
import com.dicoding.picodiploma.mygithubuserapp.databinding.ActivityProfileBinding
import com.dicoding.picodiploma.mygithubuserapp.helper.Event
import com.dicoding.picodiploma.mygithubuserapp.helper.ViewModelFactory
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.adapter.SectionPagerAdapter
import com.dicoding.picodiploma.mygithubuserapp.view.base.BaseActivity
import com.dicoding.picodiploma.mygithubuserapp.viewModel.ProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : BaseActivity() {
    private lateinit var viewModel: ProfileViewModel

    private lateinit var binding: ActivityProfileBinding

    private var isFavorite: Boolean = false
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = intent.getParcelableExtra<User>(EXTRA_USER) as User
        this.user = user
        viewModel = obtainViewModel(this)
        viewModel.userLiveData.observe(this) { setUserData(it) }
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.snackBarText.observe(this) { showSnackBar(it) }
        attachTabLayout(user)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_profile, menu)
        val favoriteButton = menu.findItem(R.id.item_favorite)
        viewModel.isFavorite.observe(this) { isFavorite ->
            this.isFavorite = isFavorite

            if (isFavorite) {
                favoriteButton.icon = AppCompatResources.getDrawable(this,
                    R.drawable.ic_baseline_favorite_filled_24)
            } else {
                favoriteButton.icon = AppCompatResources.getDrawable(this,
                    R.drawable.ic_baseline_favorite_not_filled_24)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_favorite -> {
                if (isFavorite) {
                    item.icon = AppCompatResources.getDrawable(this,
                        R.drawable.ic_baseline_favorite_not_filled_24)

                    viewModel.deleteFromFavorite()
                    val event = Event(getString(R.string.delete_from_favorite))
                    showSnackBar(event)
                } else {
                    item.icon = AppCompatResources.getDrawable(this,
                        R.drawable.ic_baseline_favorite_filled_24)
                    viewModel.insertToFavorite()
                    val event = Event(getString(R.string.insert_to_favorite))
                    showSnackBar(event)
                }

                true
            }
            else -> true
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ProfileViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, user)
        return ViewModelProvider(activity, factory)[ProfileViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        binding.detailPageProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(user: User) {
        supportActionBar?.title = user.name
        binding.imageViewUser.let { Glide.with(this).load(user.avatarUrl).into(it) }

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

        binding.tabLayoutDetail.let {
            TabLayoutMediator(it, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)
    }
}