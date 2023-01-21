package com.avantrio.assessment.adapter

import android.app.Activity
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.avantrio.assessment.R
import com.avantrio.assessment.model.Log
import com.avantrio.assessment.service.CoreApp
import com.avantrio.assessment.service.CoreApp.Companion.tinyDB
import com.avantrio.assessment.service.TinyDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class UserLogAdapter(
    private val users: List<Log>,
    activity: Activity,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun getItemCount() = users.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        when (viewType) {
            0 -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_log_header, parent, false)

                return UserLogHeaderViewHolder(view)
            }
            1 -> {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_user_log, parent, false)

                return UserLogViewHolder(view)
            }
        }
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_log, parent, false)

        return UserLogViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 0 else 1
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (position != 0) {
            holder as UserLogViewHolder
            holder.txtDate.text = users[position].date
            holder.txtTime.text = users[position].time

            if (users[position].alert_view)
                holder.txtAlert.text = "On" else holder.txtAlert.text = "off"


            if (users[position].isCalculated) {
                holder.txtCalculatedDistance.visibility = View.VISIBLE
                holder.btnCalculate.visibility = View.GONE
            } else {
                holder.txtCalculatedDistance.visibility = View.GONE
                holder.btnCalculate.visibility = View.VISIBLE
            }



            holder.btnCalculate.setOnClickListener {
                val userLatitude = tinyDB.getDouble("userLatitude", 0.0)
                val userLongitude = tinyDB.getDouble("userLongitude", 0.0)
                if (userLatitude == 0.0 || userLongitude == 0.0)
                    tinyDB.putBoolean("isLocationSaved", false)
                else {

                    val displacement = FloatArray(1)
                    Location.distanceBetween(
                        users[position].latitude,
                        users[position].longitude,
                        userLatitude,
                        userLongitude,
                        displacement
                    )

                    if (displacement.isNotEmpty())
                        tinyDB.getString("selectedUserId")?.let { selectedUserId ->
                            CoroutineScope(Dispatchers.IO).async {
                                CoreApp.userDao?.updateDistanceById(
                                    users[position].id,
                                    selectedUserId.toInt(),
                                    displacement[0].toDouble()
                                )
                            }

                        }

                }

            }
        }


    }

    inner class UserLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var txtDate: TextView = itemView.findViewById(R.id.txt_date)
        var txtTime: TextView = itemView.findViewById(R.id.txt_time)
        var txtAlert: TextView = itemView.findViewById(R.id.txt_alert)
        var imgLocation: ImageView = itemView.findViewById(R.id.img_location)
        var txtLocation: TextView = itemView.findViewById(R.id.txt_location)
        var btnCalculate: Button = itemView.findViewById(R.id.btn_calculate)
        var txtCalculatedDistance: TextView = itemView.findViewById(R.id.txt_calculated_distance)

        init {
            if (!tinyDB.getBoolean("isLocationSaved")) btnCalculate.isEnabled = false
        }


    }

    inner class UserLogHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}
