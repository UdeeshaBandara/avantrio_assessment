package com.avantrio.assessment.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.avantrio.assessment.R
import com.avantrio.assessment.activity.LoginActivity
import com.avantrio.assessment.activity.MainActivity
import com.avantrio.assessment.adapter.UserAdapter
import com.avantrio.assessment.adapter.UserLogAdapter
import com.avantrio.assessment.model.User
import com.avantrio.assessment.model.UserLog
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.ApiInterface
import com.avantrio.assessment.service.CoreApp
import com.avantrio.assessment.service.CoreApp.Companion.tinyDB
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

class UserDetailsFragment : Fragment() {


    private lateinit var repository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = UserRepository(requireContext())


        getUserLog()


        back.setOnClickListener { requireActivity().onBackPressed() }

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
                recycler_user_log.adapter = UserLogAdapter(userLog.logs, requireActivity())
                recycler_user_log.layoutManager = LinearLayoutManager(
                    activity?.applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )

            }

            override fun onError() {
                if (!isHidden)
                    (activity as MainActivity).redirectToLogin()
            }

        })


    }


}