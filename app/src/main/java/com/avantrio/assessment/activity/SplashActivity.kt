package com.avantrio.assessment.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.avantrio.assessment.R
import com.avantrio.assessment.service.CoreApp

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //Check whether previously logged in or not
        if (CoreApp.tinyDB.getBoolean("isLoggedIn"))
            startActivity(Intent(this, MainActivity::class.java))
        else
            startActivity(Intent(this, LoginActivity::class.java))

        finishAffinity()
    }
}