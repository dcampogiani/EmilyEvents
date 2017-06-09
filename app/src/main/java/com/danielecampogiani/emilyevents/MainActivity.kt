package com.danielecampogiani.emilyevents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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
                    .replace(R.id.main_container, locationFragment)
                    .commit()
        }
    }

    override fun onLocationReceived(location: LocationState.Result) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
