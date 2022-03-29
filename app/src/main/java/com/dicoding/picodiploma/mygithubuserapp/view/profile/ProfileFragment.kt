package com.dicoding.picodiploma.mygithubuserapp.view.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.ListUserFragment
import com.dicoding.picodiploma.mygithubuserapp.viewModel.FollowersViewModel
import com.dicoding.picodiploma.mygithubuserapp.viewModel.FollowingViewModel

class FollowersFragment : ListUserFragment() {
    private val viewModel by viewModels<FollowersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.value = arguments?.getParcelable<User>(EXTRA_USER) as User
        viewModel.getListUser()
        viewModel.isLoading.observe(requireActivity()) { showLoading(it) }
        viewModel.listUser.observe(requireActivity()) { setUserData(it) }
    }
}

class FollowingFragment : ListUserFragment() {
    private val viewModel by viewModels<FollowingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.value = arguments?.getParcelable<User>(EXTRA_USER) as User
        viewModel.getListUser()
        viewModel.isLoading.observe(requireActivity()) { showLoading(it) }
        viewModel.listUser.observe(requireActivity()) { setUserData(it) }
    }
}