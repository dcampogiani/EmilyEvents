package com.danielecampogiani.emilyevents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.danielecampogiani.emilyevents.events.EventsFragment
import com.danielecampogiani.emilyevents.location.LocationFragment
import com.danielecampogiani.emilyevents.location.LocationState
import com.danielecampogiani.network.facebook.FacebookEvent

class MainActivity : AppCompatActivity(), LocationFragment.OnFragmentInteractionListener, EventsFragment.OnEventSelectedListener {
    override fun onEventSelected(event: FacebookEvent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val LOCATION_FRAGMENT_TAG = "LOCATION_FRAGMENT_TAG"
        val EVENTS_FRAGMENT_TAG = "EVENTS_FRAGMENT_TAG"
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
        val eventsFragment = EventsFragment.newInstance(location.latitude.toString(), location.longitude.toString())
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, eventsFragment, EVENTS_FRAGMENT_TAG)
                .commit()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        supportFragmentManager.findFragmentByTag(LOCATION_FRAGMENT_TAG)?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
