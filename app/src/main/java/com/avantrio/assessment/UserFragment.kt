package com.avantrio.assessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avantrio.assessment.adapter.RecyclerViewClickListener
import com.avantrio.assessment.adapter.UserAdapter
import com.avantrio.assessment.model.User
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.ApiInterface
import com.avantrio.assessment.viewmodel.UserViewModel
import com.avantrio.assessment.viewmodel.UserViewModelFactory


class UserFragment : Fragment(), RecyclerViewClickListener {


    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val api = ApiInterface()
        val repository = UserRepository(api)

        factory = UserViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory)[UserViewModel::class.java]

        viewModel.getUsers()

        viewModel.users.observe(viewLifecycleOwner) { users ->
            view.findViewById<RecyclerView>(R.id.user_recycler).also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = UserAdapter(users, this)
            }
        }
    }

    override fun onRecyclerViewItemClick(view: View, user: User) {
        TODO("Not yet implemented")
    }


}