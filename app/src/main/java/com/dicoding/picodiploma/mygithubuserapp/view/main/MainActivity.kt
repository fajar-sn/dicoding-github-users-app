package com.dicoding.picodiploma.mygithubuserapp.view.main

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.commit
import com.dicoding.picodiploma.mygithubuserapp.R
import com.dicoding.picodiploma.mygithubuserapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.mygithubuserapp.model.SearchResponse
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.BaseActivity
import com.dicoding.picodiploma.mygithubuserapp.viewModel.HomeViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<HomeViewModel>()
    private var isLoading = false
    private var listUser: List<User> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            isLoading = it
            commitFragment(listUser, isLoading)
        }

        viewModel.listUser.observe(this) {
            listUser = it
            commitFragment(listUser, isLoading)
        }

        viewModel.snackBarText.observe(this) { showSnackBar(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotBlank())
                    viewModel.searchUser("$query")

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })

        return true
    }

    private fun commitFragment(listUser: List<User>, isLoading: Boolean) {
        val bundle = Bundle()
        bundle.putBoolean(HomeFragment.EXTRA_IS_LOADING, isLoading)
        bundle.putParcelable(HomeFragment.EXTRA_LIST_USER, SearchResponse(null, null, listUser))
        val mainFragment = HomeFragment()
        mainFragment.arguments = bundle
        val fragment = supportFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (!supportFragmentManager.fragments.contains(fragment)) {
            supportFragmentManager.commit {
                add(R.id.frame_container, mainFragment, HomeFragment::class.java.simpleName)
            }
        } else {
            supportFragmentManager.commit {
                replace(R.id.frame_container, mainFragment, HomeFragment::class.java.simpleName)
            }
        }
    }
}