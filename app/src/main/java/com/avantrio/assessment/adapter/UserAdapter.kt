package com.avantrio.assessment.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.avantrio.assessment.activity.MainActivity
import com.avantrio.assessment.R
import com.avantrio.assessment.model.User
import com.avantrio.assessment.service.CoreApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class UserAdapter(
    private var users: List<User>,
    activity: Activity,
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    var activity = activity
    var isDescending = false

    fun sortListByAscendingOrDescending() {

        users = if (isDescending)
            users.sortedBy { it.name }
        else
            users.sortedByDescending { it.name }
        isDescending = !isDescending
        notifyDataSetChanged()

    }

    override fun getItemCount() = users.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {

        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user, parent, false)

        return UserViewHolder(view)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {


        holder.name.text = users[position].name

        holder.imgFav.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                CoreApp.userDao?.changeFavStatus(users[position].id, !users[position].isFav)

            }
        }
        holder.rootView.setOnClickListener {

            (activity as MainActivity).showUserLogFragment(
                users[position].id.toString(),
                users[position].name
            )
        }

//        holder.itemUserBinding.buttonBook.setOnClickListener {
//            listener.onRecyclerViewItemClick(holder.itemUserBinding.buttonBook, movies[position])
//        }
//        holder.itemUserBinding.layoutLike.setOnClickListener {
//            listener.onRecyclerViewItemClick(holder.itemUserBinding.layoutLike, movies[position])
//        }
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var rootView: RelativeLayout = itemView.findViewById(R.id.root_view)
        var name: TextView = itemView.findViewById(R.id.name)
        var imgFav: ImageView = itemView.findViewById(R.id.img_fav)
        var imgMore: ImageView = itemView.findViewById(R.id.img_more)


    }


}

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClick(view: View, user: User)
    fun onFavClick(user: User)
}