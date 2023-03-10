package com.avantrio.assessment.service

import com.avantrio.assessment.model.Login
import com.avantrio.assessment.model.User
import com.avantrio.assessment.model.UserLog
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiInterface {

    @POST("user/login")
    fun login(
        @Body postDetails: HashMap<String, String>
    ): Call<Login>

    @GET("users")
    fun getUsers(
        @Header("Authorization") token: String,
    ): Call<List<User>>

    @GET("user/{id}/logs")
    fun getUserLog(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<UserLog>


    companion object {

        private val BASE_URL = "http://apps.avantrio.xyz:8010/api/"

        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }

    }
}