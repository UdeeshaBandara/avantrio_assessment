package com.avantrio.assessment.viewmodel

import android.app.Application
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.avantrio.assessment.model.User
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.TinyDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {


    private val _email: String? = null
    val email: String?
        get() = _email

    private val _password: String? = null
    val password: String?
        get() = _password

    fun onButtonClicked() {
        if (isValid()) {
            CoroutineScope(Dispatchers.Main).launch {
                val data = CoroutineScope(Dispatchers.IO).async rt@{
                    return@rt _email?.let { email ->
                        _password?.let { pass ->
                            repository.userLogin(
                                email,
                                pass
                            )
                        }
                    }
                }.await()

            }

        }
    }

    private fun isValid(): Boolean {
        return (!TextUtils.isEmpty(_email) && Patterns.EMAIL_ADDRESS.matcher(_email)
            .matches()
                && _password?.length!! > 5)
    }

}