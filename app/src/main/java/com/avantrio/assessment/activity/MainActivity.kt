package com.avantrio.assessment.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.avantrio.assessment.R
import com.avantrio.assessment.fragment.SettingsFragment
import com.avantrio.assessment.fragment.UserDetailsFragment
import com.avantrio.assessment.fragment.UserFragment
import com.avantrio.assessment.service.CoreApp.Companion.tinyDB

class MainActivity : AppCompatActivity() {

    lateinit var mainFragmentManager: FragmentManager

    private val userFragment: UserFragment = UserFragment()
    private val settingsFragment: SettingsFragment = SettingsFragment()
    private val userDetailsFragment: UserDetailsFragment = UserDetailsFragment()

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

    }
    fun changeFragment(selectedUserId : String,selectedUserName: String){

        tinyDB.putString("selectedUserId",selectedUserId)
        tinyDB.putString("selectedUserName",selectedUserName)
        mainFragmentManager.beginTransaction()
            .hide(mainFragmentManager.findFragmentByTag(userFragment.javaClass.name)!!)
            .show(mainFragmentManager.findFragmentByTag(userDetailsFragment.javaClass.name)!!).addToBackStack(userDetailsFragment.javaClass.name).commit()

    }
}