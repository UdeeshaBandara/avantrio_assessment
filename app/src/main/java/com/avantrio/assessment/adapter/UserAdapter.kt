package com.avantrio.assessment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.avantrio.assessment.R
import com.avantrio.assessment.databinding.ItemUserBinding
import com.avantrio.assessment.model.User

class UserAdapter(
    private val users: User,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun getItemCount() = users.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemUserBinding.user = users[position]
//        holder.itemUserBinding.buttonBook.setOnClickListener {
//            listener.onRecyclerViewItemClick(holder.itemUserBinding.buttonBook, movies[position])
//        }
//        holder.itemUserBinding.layoutLike.setOnClickListener {
//            listener.onRecyclerViewItemClick(holder.itemUserBinding.layoutLike, movies[position])
//        }
    }


    inner class UserViewHolder(
        val itemUserBinding: ItemUserBinding
    ) : RecyclerView.ViewHolder(itemUserBinding.root)
}

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, user: User)
}