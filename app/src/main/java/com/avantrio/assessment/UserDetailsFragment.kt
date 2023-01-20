package com.avantrio.assessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.avantrio.assessment.adapter.UserLogAdapter
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.ApiInterface
import com.avantrio.assessment.service.CoreApp
import com.avantrio.assessment.service.CoreApp.Companion.tinyDB
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserDetailsFragment : Fragment() {

    private val api = ApiInterface()
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

        repository = UserRepository(api, requireContext())


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
        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt repository.getUserLog()
            }.await()
            CoroutineScope(Dispatchers.IO).async {


                CoreApp.userDao?.insertAllUserLogs(data?.logs)
            }


            data.let {
//                _users.value = it
                recycler_user_log.adapter = UserLogAdapter(it!!.logs, requireActivity())
                recycler_user_log.layoutManager = LinearLayoutManager(
                    activity?.applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }


        }

    }


}