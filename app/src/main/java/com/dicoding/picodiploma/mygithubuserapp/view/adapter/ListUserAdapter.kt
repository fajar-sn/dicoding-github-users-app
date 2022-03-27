package com.dicoding.picodiploma.mygithubuserapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.mygithubuserapp.databinding.ItemRowUserBinding
import com.dicoding.picodiploma.mygithubuserapp.model.User

class ListUserAdapter(private val listUser: List<User>) :
    RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =
            ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.binding.textViewItemName.text = user.login
        holder.binding.textViewItemUrl.text = user.htmlUrl

        Glide.with(holder.itemView.context).load(user.avatarUrl).circleCrop()
            .into(holder.binding.imageViewUser)

        holder.binding.cardView.setOnClickListener { onItemClickCallBack.onItemClicked(listUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallBack {
        fun onItemClicked(data: User)
    }
}