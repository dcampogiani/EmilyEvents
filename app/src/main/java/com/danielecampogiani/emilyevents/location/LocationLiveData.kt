package com.danielecampogiani.emilyevents.location

import android.arch.lifecycle.LiveData
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import utils.SimpleLocationListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationLiveData @Inject constructor(context: Context) : LiveData<Location>() {

    private val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val locationListener: LocationListener = object : SimpleLocationListener() {
        override fun onLocationChanged(location: Location) {
            value = location
        }
    }


    override fun onActive() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)
    }

    override fun onInactive() {
        locationManager.removeUpdates(locationListener)
    }
}