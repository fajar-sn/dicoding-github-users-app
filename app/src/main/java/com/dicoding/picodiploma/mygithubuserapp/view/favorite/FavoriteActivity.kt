package com.dicoding.picodiploma.mygithubuserapp.view.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.dicoding.picodiploma.mygithubuserapp.R
import com.dicoding.picodiploma.mygithubuserapp.databinding.ActivityFavoriteBinding
import com.dicoding.picodiploma.mygithubuserapp.helper.ActivityHelper
import com.dicoding.picodiploma.mygithubuserapp.view.settings.SettingsActivity
import com.dicoding.picodiploma.mygithubuserapp.viewModel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorites"
        val viewModel: FavoriteViewModel = ActivityHelper.obtainViewModel(this, null)

        viewModel.userList.observe(this) {
            ActivityHelper.commitFragment(it,
                supportFragmentManager,
                R.id.frame_layout_favorite,
                FavoriteFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.item_settings_favorite -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
        else -> true
    }
}