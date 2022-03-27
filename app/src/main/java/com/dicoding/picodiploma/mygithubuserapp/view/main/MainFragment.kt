package com.dicoding.picodiploma.mygithubuserapp.view.main

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
import com.dicoding.picodiploma.mygithubuserapp.databinding.FragmentMainBinding
import com.dicoding.picodiploma.mygithubuserapp.model.User
import com.dicoding.picodiploma.mygithubuserapp.view.adapter.ListUserAdapter
import com.dicoding.picodiploma.mygithubuserapp.view.profile.ProfileActivity

class MainFragment(private val listUser: LiveData<List<User>>, private val isLoading: LiveData<Boolean>) : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewListUser.setHasFixedSize(true)

        if (requireActivity().applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerViewListUser.layoutManager = GridLayoutManager(requireContext(), 2)
        } else {
            binding.recyclerViewListUser.layoutManager = LinearLayoutManager(requireContext())
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
        binding.recyclerViewListUser.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallBack {
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