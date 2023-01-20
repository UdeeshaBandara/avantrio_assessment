package com.avantrio.assessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.avantrio.assessment.adapter.UserAdapter
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.ApiInterface
import com.avantrio.assessment.service.CoreApp
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class UserFragment : Fragment() {


    private val api = ApiInterface()
    private lateinit var repository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = UserRepository(api, requireContext())
        getUsers()

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

    }

    private fun getUsers() {


        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt repository.getUsers()
            }.await()
            CoroutineScope(Dispatchers.IO).async {


                CoreApp.userDao?.insertAll(data)
            }


            data.let {

                user_recycler.adapter = UserAdapter(it!!, requireActivity())
                user_recycler.layoutManager = LinearLayoutManager(
                    activity?.applicationContext,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }


        }

    }


}