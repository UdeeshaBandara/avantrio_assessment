package com.avantrio.assessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avantrio.assessment.model.User
import com.avantrio.assessment.repositories.UserRepository

class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {


    private val _users = MutableLiveData<User>()
    val users: LiveData<User>
        get() = _users
}