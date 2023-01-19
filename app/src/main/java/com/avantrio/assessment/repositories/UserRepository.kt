package com.avantrio.assessment.repositories

import android.content.Context
import com.avantrio.assessment.model.Login
import com.avantrio.assessment.room.UserDao
import com.avantrio.assessment.service.ApiInterface
import com.avantrio.assessment.service.TinyDB

class UserRepository(
    private val api: ApiInterface,
    ctx: Context

) {
    private var tinyDB: TinyDB = TinyDB(ctx)

    private lateinit var accessToken: String

    init {
        tinyDB.getString("access_token")?.let {
            accessToken = it
        }
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

    suspend fun getUserLog(userId: String) =

        api.getUserLog(
            accessToken,
            userId
        )
            .body()

}