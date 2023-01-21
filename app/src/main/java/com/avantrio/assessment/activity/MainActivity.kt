package com.avantrio.assessment.activity

import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
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

        //Adding fragment to fragment manger when activity creating
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

        hideOverlay()
    }


    fun changeFragment(newFragmentTag: String) {

        mainFragmentManager.beginTransaction()
            .hide(mainFragmentManager.findFragmentByTag(currentFragmentTag)!!)
            .show(mainFragmentManager.findFragmentByTag(newFragmentTag)!!).commit()
        currentFragmentTag = newFragmentTag

    }

    //Redirect to login activity on session timeout
    fun redirectToLogin() {
        tinyDB.putBoolean("isLoggedIn", false)
        Toast.makeText(this, "Session timeout. Please log again", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finishAffinity()
    }

    //Hide bottom navigation when appearing physical keyboard
    private fun hideOverlay() {
        rlt_root.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            rlt_root.getWindowVisibleDisplayFrame(r)
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val heightDiff = displayMetrics.heightPixels - (r.bottom - r.top)
            if (heightDiff > 100) {


                bottom_navigation.visibility =
                    View.INVISIBLE


            } else {


                bottom_navigation.visibility =
                    View.VISIBLE


            }

        }
    }
}