package com.avantrio.assessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.ApiInterface

class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val api = ApiInterface()
        val repository = UserRepository(api, this)



    }
}