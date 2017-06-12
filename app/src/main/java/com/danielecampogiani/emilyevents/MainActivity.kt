package com.danielecampogiani.emilyevents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.danielecampogiani.emilyevents.location.LocationFragment
import com.danielecampogiani.emilyevents.location.LocationState

class MainActivity : AppCompatActivity(), LocationFragment.OnFragmentInteractionListener {

    companion object {
        val LOCATION_FRAGMENT_TAG = "LOCATION_FRAGMENT_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentByTag = supportFragmentManager.findFragmentByTag(LOCATION_FRAGMENT_TAG)

        if (fragmentByTag == null) {
            val locationFragment = LocationFragment.newInstance()
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, locationFragment, LOCATION_FRAGMENT_TAG)
                    .commit()
        }
    }

    override fun onLocationReceived(location: LocationState.Result) {
        Toast.makeText(this, location.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        supportFragmentManager.findFragmentByTag(LOCATION_FRAGMENT_TAG)?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
