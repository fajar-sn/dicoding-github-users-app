package com.dicoding.picodiploma.mygithubuserapp.view.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.mygithubuserapp.model.SearchResponse
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.ListUserFragment

class HomeFragment : ListUserFragment() {
    private lateinit var listUser: List<User>
    private var isLoading: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (requireActivity().applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerViewListUser.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            binding.recyclerViewListUser.layoutManager = LinearLayoutManager(requireActivity())
        }

        if (arguments == null) return
        isLoading = requireArguments().getBoolean(EXTRA_IS_LOADING)
        listUser = requireArguments().getParcelable<SearchResponse>(EXTRA_LIST_USER)?.users ?: listOf()
        showLoading(isLoading)
        setUserData(listUser)

    }

    companion object {
        const val EXTRA_LIST_USER = "extra_list_user"
        const val EXTRA_IS_LOADING = "extra_is_loading"
    }
}