package com.avantrio.assessment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.ApiInterface
import com.avantrio.assessment.viewmodel.UserViewModel
import com.avantrio.assessment.viewmodel.UserViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val api = ApiInterface()
        val repository = UserRepository(api, this)

        factory = UserViewModelFactory(repository)
        viewModel = ViewModelProviders.of(this, factory)[UserViewModel::class.java]


    }
}