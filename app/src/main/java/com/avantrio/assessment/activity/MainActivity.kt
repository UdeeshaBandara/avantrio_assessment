package com.avantrio.assessment.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.avantrio.assessment.R
import com.avantrio.assessment.fragment.SettingsFragment
import com.avantrio.assessment.fragment.UserDetailsFragment
import com.avantrio.assessment.fragment.UserFragment
import com.avantrio.assessment.service.CoreApp
import com.avantrio.assessment.service.CoreApp.Companion.tinyDB
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mainFragmentManager: FragmentManager

    private val userFragment: UserFragment = UserFragment()
    private val settingsFragment: SettingsFragment = SettingsFragment()
    private val userDetailsFragment: UserDetailsFragment = UserDetailsFragment()
    private var currentFragmentTag = userFragment.javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragmentManager = supportFragmentManager

        if (savedInstanceState == null)
            mainFragmentManager.beginTransaction().add(
                R.id.fragment_container,
                userFragment,
                userFragment.javaClass.name
            ).add(
                R.id.fragment_container,
                settingsFragment,
                settingsFragment.javaClass.name
            ).hide(settingsFragment).add(
                R.id.fragment_container,
                userDetailsFragment,
                userDetailsFragment.javaClass.name
            ).hide(userDetailsFragment).commit()

        lnr_settings.setOnClickListener {
            changeFragment(settingsFragment.javaClass.name)
        }
        lnr_users.setOnClickListener {
            changeFragment(userFragment.javaClass.name)
        }
    }

    fun showUserLogFragment(selectedUserId: String, selectedUserName: String) {

        tinyDB.putString("selectedUserId", selectedUserId)
        tinyDB.putString("selectedUserName", selectedUserName)
        mainFragmentManager.beginTransaction()
            .hide(mainFragmentManager.findFragmentByTag(currentFragmentTag)!!)
            .show(mainFragmentManager.findFragmentByTag(userDetailsFragment.javaClass.name)!!)
            .addToBackStack(userDetailsFragment.javaClass.name).commit()
        currentFragmentTag = userDetailsFragment.javaClass.name

    }

    private fun changeFragment(newFragmentTag: String) {

        if (mainFragmentManager.backStackEntryCount > 0) onBackPressed()

        mainFragmentManager.beginTransaction()
            .hide(mainFragmentManager.findFragmentByTag(currentFragmentTag)!!)
            .show(mainFragmentManager.findFragmentByTag(newFragmentTag)!!).commit()
        currentFragmentTag = newFragmentTag

    }

    fun redirectToLogin() {
        tinyDB.putBoolean("isLoggedIn", false)
        Toast.makeText(this, "Session timeout. Please log again", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }
}