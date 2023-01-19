package com.avantrio.assessment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avantrio.assessment.model.Log
import com.avantrio.assessment.model.User
import com.avantrio.assessment.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class UserLogViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _userLog = MutableLiveData<List<Log>>()
    val userLog: LiveData<List<Log>>
        get() = _userLog

    lateinit var selectedUserId: String

    fun getUserLog() {

        CoroutineScope(Dispatchers.Main).launch {
            val data = CoroutineScope(Dispatchers.IO).async rt@{
                return@rt repository.getUserLog(selectedUserId)
            }.await()
            _userLog.value = data?.logs

        }

    }

}