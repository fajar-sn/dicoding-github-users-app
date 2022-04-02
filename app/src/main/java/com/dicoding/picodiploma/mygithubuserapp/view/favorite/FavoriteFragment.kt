package com.dicoding.picodiploma.mygithubuserapp.view.favorite

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.mygithubuserapp.model.SearchResponse
import com.dicoding.picodiploma.mygithubuserapp.view.base.ListUserFragment

class FavoriteFragment : ListUserFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (arguments == null) return
        super.onViewCreated(view, savedInstanceState)

        if (requireActivity().applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerViewListUser.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            binding.recyclerViewListUser.layoutManager = LinearLayoutManager(requireActivity())
        }

        val listUser =
            requireArguments().getParcelable<SearchResponse>(EXTRA_LIST_USER)?.users ?: listOf()
        setUserData(listUser)
    }
}