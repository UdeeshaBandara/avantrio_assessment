package com.avantrio.assessment.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.avantrio.assessment.R
import com.avantrio.assessment.activity.MainActivity
import com.avantrio.assessment.fragment.UserDetailsFragment
import com.avantrio.assessment.model.User
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.CoreApp
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.util.*

class UserAdapter(
    private var users: List<User>,
    val activity: Activity,
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    private var isDescending = false
    private var usersOriginal = users

    init {
        //Assigning random colors for user monogram
        val rnd = Random()
        users.forEachIndexed { index, _ ->
            users[index].monogramColor =
                Color.argb(
                    255,
                    rnd.nextInt(256),
                    rnd.nextInt(256),
                    rnd.nextInt(256)
                )
        }

    }

    fun sortListByAscendingOrDescending() {

        users = if (isDescending)
            users.sortedBy { it.name }
        else
            users.sortedByDescending { it.name }
        isDescending = !isDescending
        notifyDataSetChanged()

    }

    fun filterFavUsers() {

        users = users.filter { it.isFav }
        notifyDataSetChanged()

    }

    fun resetFavUsers() {

        users = usersOriginal
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
        holder.txtMonogram.text = users[position].name[0].toString()

        holder.monogramCard.setCardBackgroundColor(
            users[position].monogramColor
        )

        if (users[position].isFav) holder.imgFav.setImageDrawable(
            ContextCompat.getDrawable(
                activity,
                R.drawable.heart_fill_icon
            )
        )
        else
            holder.imgFav.setImageDrawable(
                ContextCompat.getDrawable(
                    activity,
                    R.drawable.heart_icon
                )
            )

        holder.imgFav.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {
                CoreApp.userDao?.changeFavStatus(users[position].name, !users[position].isFav)

                CoroutineScope(Dispatchers.Main).async {
                    users[position].isFav = !users[position].isFav
                    notifyItemChanged(position)
                }
            }

        }
        //Adding details and navigate to user log fragment
        holder.rootView.setOnClickListener {
            CoreApp.tinyDB.putString("selectedUserId", users[position].id.toString())
            CoreApp.tinyDB.putString("selectedUserName", users[position].name)


            (activity as MainActivity).changeFragment(
                UserDetailsFragment().javaClass.name
            )
        }

    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var rootView: RelativeLayout = itemView.findViewById(R.id.root_view)
        var name: TextView = itemView.findViewById(R.id.name)
        var txtMonogram: TextView = itemView.findViewById(R.id.txt_monogram)
        var monogramCard: CardView = itemView.findViewById(R.id.monogram_card)
        var imgFav: ImageView = itemView.findViewById(R.id.img_fav)


    }


}
