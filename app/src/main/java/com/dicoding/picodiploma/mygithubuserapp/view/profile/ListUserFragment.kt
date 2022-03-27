package com.dicoding.picodiploma.mygithubuserapp.view.profile

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.mygithubuserapp.databinding.FragmentListUserProfileBinding
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.adapter.ListUserAdapter

class ListUserFragment(
    private val listUser: LiveData<List<User>>,
    private val isLoading: LiveData<Boolean>,
) : Fragment() {
    private var _binding: FragmentListUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUserProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewListFollowers.setHasFixedSize(true)

        if (requireActivity().applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerViewListFollowers.layoutManager =
                GridLayoutManager(requireActivity(), 2)
        } else {
            binding.recyclerViewListFollowers.layoutManager = object : LinearLayoutManager(requireActivity()) {
                override fun canScrollVertically() = false
            }
        }

        isLoading.observe(requireActivity()) { showLoading(it) }
        listUser.observe(requireActivity()) { setUserData(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(users: List<User>) {
        val listUserAdapter = ListUserAdapter(users)
        binding.recyclerViewListFollowers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object :
            ListUserAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: User) {
                openUserDetailPage(data)
            }
        })
    }

    private fun openUserDetailPage(user: User) {
        val intent = Intent(requireActivity(), ProfileActivity::class.java)
        intent.putExtra(ProfileActivity.EXTRA_USER, user)
        startActivity(intent)
    }
}