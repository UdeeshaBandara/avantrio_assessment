package com.avantrio.assessment.repositories

import com.avantrio.assessment.service.ApiInterface

class UserRepository(
    private val api: ApiInterface
) {
    suspend fun getUsers() {

        api.getUsers("").body()

    }
}