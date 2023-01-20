package com.avantrio.assessment.repositories

import android.content.Context
import com.avantrio.assessment.model.Login
import com.avantrio.assessment.room.UserDao
import com.avantrio.assessment.service.ApiInterface
import com.avantrio.assessment.service.CoreApp
import com.avantrio.assessment.service.TinyDB

class UserRepository(
    private val api: ApiInterface,
    ctx: Context

) {
    private var tinyDB: TinyDB = TinyDB(ctx)

    private lateinit var accessToken: String

    init {
//        tinyDB.getString("access_token")?.let {
//            accessToken = it
//        }
        accessToken =
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJ1c2VybmFtZSI6ImFjaGFsYSIsImV4cCI6MTY3NDE4MDE1MiwiZW1haWwiOiJhY2hhbGFAbWFpbGluYXRvci5jb20iLCJvcmlnX2lhdCI6MTY3NDE3NjU1Mn0.MXialLRSSQqnsO8Xw5n8kKMmxLH6YIT8Kp96YUg_kSg"
    }

    suspend fun getUsers() = api.getUsers(accessToken)
        .body()


    suspend fun userLogin(email: String, password: String): Login? {

        return api.login(
            postDetails = mutableMapOf(
                "username" to email,
                "password" to password,
            ) as HashMap<String, String>
        )
            .body()


    }

    suspend fun getUserLog() =

        api.getUserLog(
            accessToken,
            tinyDB.getString("selectedUserId")!!
        )
            .body()

}