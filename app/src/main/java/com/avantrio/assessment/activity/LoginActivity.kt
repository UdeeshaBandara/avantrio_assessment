package com.avantrio.assessment.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.avantrio.assessment.R
import com.avantrio.assessment.databinding.ActivityLoginBinding
import com.avantrio.assessment.repositories.UserRepository
import com.avantrio.assessment.service.CoreApp
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.loginHandler = LoginHandler(this, UserRepository())


    }

    private fun isValid(): Boolean {
        return (!TextUtils.isEmpty(binding.edtEmail.text)
                && binding.edtPassword.text?.length!! > 5)
    }

    inner class LoginHandler(private val ctx: Context, private val repository: UserRepository) {

        fun onLoginClick() {

            if (isValid()) {
                repository.userLogin(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString(), object : UserRepository.NetworkCallback {
                        override fun onSuccess(response: Response<Any>) {

                            if (response.code() == 200) {
                                startActivity(Intent(ctx, MainActivity::class.java))
                                finishAffinity()
                            }else{
                                Toast.makeText(ctx, "Invalid credentials", Toast.LENGTH_LONG).show()
                            }

                        }

                        override fun onError() {

                        }

                    })


            } else {

                Toast.makeText(ctx, "Email or password not valid", Toast.LENGTH_LONG).show()
            }

        }
    }
}

