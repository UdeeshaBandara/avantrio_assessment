package com.avantrio.assessment.fragment

import android.app.AlertDialog
import android.content.Intent
import android.location.Address
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.avantrio.assessment.R
import com.avantrio.assessment.activity.LoginActivity
import com.avantrio.assessment.service.CoreApp
import com.avantrio.assessment.service.CoreApp.Companion.tinyDB
import com.avantrio.assessment.service.PermissionHandler
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btn_log_out.setOnClickListener {
          showConfirmationAndLogout()

        }


        btn_update_location.setOnClickListener {

            //updating location from settings page
            PermissionHandler(requireActivity()).getLocation(object :
                PermissionHandler.LocationPermissionCallback {
                override fun onSuccess(location: Location, addressText: String) {

                    tinyDB.putString("userAddress", addressText)
                    tinyDB.putBoolean("isLocationSaved", true)
                    tinyDB.putDouble("userLatitude", location.latitude)
                    tinyDB.putDouble("userLongitude", location.longitude)
                    txt_user_address.text = tinyDB.getString("userAddress")
                }

            })


        }
        txt_email.text = tinyDB.getString("userEmail")
        txt_user_address.text = tinyDB.getString("userAddress")

    }

    private fun showConfirmationAndLogout() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Are you sure you want to log out?")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ ->
                tinyDB.putBoolean("isLoggedIn", false)
                startActivity(Intent(requireActivity(), LoginActivity::class.java))
                requireActivity().finishAffinity()

            }
            .setNegativeButton("No") { dialog, id ->

                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }


}