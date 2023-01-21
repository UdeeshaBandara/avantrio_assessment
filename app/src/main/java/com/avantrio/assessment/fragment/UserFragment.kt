package com.avantrio.assessment.fragment


import android.location.Address
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import com.avantrio.assessment.R
import com.avantrio.assessment.activity.MainActivity
import com.avantrio.assessment.adapter.UserAdapter
import com.avantrio.assessment.model.User
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.CoreApp
import com.avantrio.assessment.service.PermissionHandler
import com.avantrio.assessment.service.TinyDB
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_user.*
import retrofit2.Response

class UserFragment : Fragment() {


    private lateinit var repository: UserRepository

    private lateinit var tinyDB: TinyDB
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tinyDB = TinyDB(requireActivity())
        repository = UserRepository()
        getUsers()

        img_location.setOnClickListener {
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
        img_fav_filter.setOnClickListener {
            if (img_fav_filter.tag.equals("notFav")) {
                img_fav_filter.tag = "fav"
                img_fav_filter.setImageDrawable(
                    getDrawable(
                        requireActivity(),
                        R.drawable.heart_fill_icon
                    )
                )
            } else {
                img_fav_filter.tag = "notFav"
                img_fav_filter.setImageDrawable(
                    getDrawable(
                        requireActivity(),
                        R.drawable.heart_icon
                    )
                )
            }


        }
        img_sort.setOnClickListener {
            userAdapter.sortListByAscendingOrDescending()

        }
    }


    private fun getUsers() {

        repository.getUsers(object : UserRepository.NetworkCallback {
            override fun onSuccess(response: Response<Any>) {
                val loginResponse = response.body() as List<User>
                userAdapter = UserAdapter(loginResponse, requireActivity())
                user_recycler.adapter = userAdapter
                user_recycler.layoutManager = LinearLayoutManager(
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