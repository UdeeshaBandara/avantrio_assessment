package com.avantrio.assessment.fragment


import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.avantrio.assessment.R
import com.avantrio.assessment.activity.MainActivity
import com.avantrio.assessment.adapter.UserLogAdapter
import com.avantrio.assessment.model.Log
import com.avantrio.assessment.model.UserLog
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.CoreApp.Companion.tinyDB
import com.avantrio.assessment.service.PermissionHandler
import kotlinx.android.synthetic.main.fragment_user_details.*
import retrofit2.Response

class UserDetailsFragment : Fragment() {


    private lateinit var repository: UserRepository
    private lateinit var userLogAdapter: UserLogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = UserRepository()


        getUserLog()


        back.setOnClickListener {
            (activity as MainActivity).changeFragment(
                UserFragment().javaClass.name
            )
        }

        img_location.setOnClickListener {
            //updating location details
            PermissionHandler(requireActivity()).getLocation(object :
                PermissionHandler.LocationPermissionCallback {
                override fun onSuccess(location: Location, addressText: String) {

                    tinyDB.putString(
                        "userAddress",
                        addressText
                    )


                    tinyDB.putBoolean("isLocationSaved", true)
                    tinyDB.putDouble("userLatitude", location.latitude)
                    tinyDB.putDouble("userLongitude", location.longitude)

                }

            })

        }
        img_sort.setOnClickListener {
            userLogAdapter.sortListByAscendingOrDescending()

        }

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            getUserLog()
    }

    private fun getUserLog() {

        user_name.text = tinyDB.getString("selectedUserName")

        repository.getUserLog(object : UserRepository.NetworkCallback {
            override fun onSuccess(response: Response<Any>) {
                val userLog = response.body() as UserLog
                userLog.logs.toMutableList()
                    .add(Log(false, "", 0, 0.0, 0.0, "0.0", "", false, 0.0, false))
                userLogAdapter = UserLogAdapter(userLog.logs, requireActivity())
                recycler_user_log.adapter = userLogAdapter
                recycler_user_log.layoutManager = LinearLayoutManager(
                    activity?.applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )

            }

            override fun onError() {
                if (!isHidden && isAdded)
                    (activity as MainActivity).redirectToLogin()
            }

        })


    }


}