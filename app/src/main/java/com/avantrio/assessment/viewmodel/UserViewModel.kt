package com.avantrio.assessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avantrio.assessment.model.User
import com.avantrio.assessment.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {


    private val _users = MutableLiveData<User>()
    val users: LiveData<User>
        get() = _users

    fun getUsers() {

        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt repository.getUsers()
            }.await()
            _users.value = data!!

        }

    }

}