package com.dicoding.picodiploma.mygithubuserapp.view.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dicoding.picodiploma.mygithubuserapp.R
import com.dicoding.picodiploma.mygithubuserapp.database.SettingsPreferences
import com.dicoding.picodiploma.mygithubuserapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.mygithubuserapp.helper.ActivityHelper
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.base.BaseActivity
import com.dicoding.picodiploma.mygithubuserapp.view.favorite.FavoriteActivity
import com.dicoding.picodiploma.mygithubuserapp.view.settings.SettingsActivity
import com.dicoding.picodiploma.mygithubuserapp.viewModel.HomeViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
    private var isLoading = false
    private var listUser: List<User> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val preferences = SettingsPreferences.getInstance(dataStore)
        viewModel = ActivityHelper.obtainViewModel(this, preferences)

        viewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        viewModel.isLoading.observe(this) {
            isLoading = it

            ActivityHelper.commitFragment(listUser,
                isLoading,
                supportFragmentManager,
                R.id.frame_layout_home,
                HomeFragment())
        }

        viewModel.listUser.observe(this) {
            listUser = it

            ActivityHelper.commitFragment(listUser,
                isLoading,
                supportFragmentManager,
                R.id.frame_layout_home,
                HomeFragment())
        }

        viewModel.snackBarText.observe(this) { showSnackBar(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotBlank())
                    viewModel.searchUser("$query")

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?) = false
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.item_favorite -> {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
            true
        }
        R.id.item_settings_home -> {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            true
        }
        else -> true
    }
}