package com.avantrio.assessment.adapter

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.avantrio.assessment.MainActivity
import com.avantrio.assessment.R
import com.avantrio.assessment.model.Log
import com.avantrio.assessment.model.User
import com.avantrio.assessment.service.CoreApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class UserLogAdapter(
    private val users: List<Log>,
    activity: Activity,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var activity = activity

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


    }

    inner class UserLogHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}
