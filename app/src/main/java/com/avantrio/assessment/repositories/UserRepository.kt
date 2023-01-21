package com.avantrio.assessment.repositories

import android.content.Context
import android.util.Log
import com.avantrio.assessment.model.Login
import com.avantrio.assessment.model.User
import com.avantrio.assessment.model.UserLog
import com.avantrio.assessment.service.ApiInterface
import com.avantrio.assessment.service.CoreApp
import com.avantrio.assessment.service.CoreApp.Companion.tinyDB
import com.avantrio.assessment.service.TinyDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
class UserRepository(

    ctx: Context

) {


    private lateinit var accessToken: String

    init {
        tinyDB.getString("accessToken")?.let {
            accessToken = it
        }


    }

    fun getUsers(callbackTo: NetworkCallback) {

        fetchData(ApiInterface.create().getUsers(
            accessToken
        ) as Call<Any>, callback = object : NetworkCallback {
            override fun onSuccess(response: Response<Any>) {
                val loginResponse = response.body() as List<User>
                CoroutineScope(Dispatchers.IO).async {
                    CoreApp.userDao?.insertAll(loginResponse)
                }
                callbackTo.onSuccess(response)
            }

            override fun onError() {
                callbackTo.onError()
            }

        })

    }


    fun userLogin(email: String, password: String, callbackTo: NetworkCallback) {


        fetchData(ApiInterface.create().login(
            postDetails = mutableMapOf(
                "username" to email,
                "password" to password,
            ) as HashMap<String, String>
        ) as Call<Any>, callback = object : NetworkCallback {
            override fun onSuccess(response: Response<Any>) {

                if (response.code() == 200) {
                    val loginResponse = response.body() as Login
                    tinyDB.putString("accessToken", "Bearer " + loginResponse.token)
                    tinyDB.putBoolean("isLoggedIn", true)
                    tinyDB.putString("userEmail", email)
                }


                callbackTo.onSuccess(response)
            }

            override fun onError() {
                callbackTo.onError()
            }

        })


    }

    fun getUserLog(callbackTo: NetworkCallback) {
        fetchData(ApiInterface.create().getUserLog(
            accessToken,
            tinyDB.getString("selectedUserId")!!
        ) as Call<Any>, callback = object : NetworkCallback {
            override fun onSuccess(response: Response<Any>) {
                val userLog = response.body() as UserLog
                CoroutineScope(Dispatchers.IO).async {
                    CoreApp.userDao?.insertAllUserLogs(userLog.logs)
                }
                callbackTo.onSuccess(response)
            }

            override fun onError() {
                callbackTo.onError()
            }

        })

    }

    private fun fetchData(apiInterface: Call<Any>, callback: NetworkCallback) {


        apiInterface.enqueue(
            object : Callback<Any> {
                override fun onResponse(
                    call: Call<Any>,
                    response: Response<Any>
                ) {
                    if (response.code() == 200 || response.code() == 400)
                        callback.onSuccess(response)
                    else
                        callback.onError()

                }

                override fun onFailure(call: Call<Any>, t: Throwable) {

                    Log.e("fail", t.message.toString())
                    callback.onError()
                }
            })
    }

    interface NetworkCallback {
        fun onSuccess(response: Response<Any>)
        fun onError()
    }
}