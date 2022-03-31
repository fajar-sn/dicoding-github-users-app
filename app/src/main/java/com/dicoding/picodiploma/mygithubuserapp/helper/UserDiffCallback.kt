package com.dicoding.picodiploma.mygithubuserapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.picodiploma.mygithubuserapp.model.User

class UserDiffCallback(private val oldListUser: List<User>, private val newListUser: List<User>) :
    DiffUtil.Callback() {
    override fun getOldListSize() = oldListUser.size

    override fun getNewListSize() = newListUser.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldListUser[oldItemPosition].id == newListUser[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldListUser[oldItemPosition]
        val newUser = newListUser[newItemPosition]
        return oldUser.company == newUser.company && oldUser.location == newUser.location
    }
}