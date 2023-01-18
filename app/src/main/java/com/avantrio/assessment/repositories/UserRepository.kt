package com.avantrio.assessment.repositories

import com.avantrio.assessment.model.Login
import com.avantrio.assessment.service.ApiInterface

class UserRepository(
    private val api: ApiInterface
) {
    suspend fun getUsers() =

        api.getUsers("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyLCJ1c2VybmFtZSI6ImFjaGFsYSIsImV4cCI6MTY3NDA3MjUzMCwiZW1haWwiOiJhY2hhbGFAbWFpbGluYXRvci5jb20iLCJvcmlnX2lhdCI6MTY3NDA2ODkzMH0.lau2hW8UlntfHfP_3tMnilW_Qin4RSjjCwXVrtERumE")
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

}