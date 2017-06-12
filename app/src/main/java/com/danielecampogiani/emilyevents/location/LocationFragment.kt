package com.danielecampogiani.emilyevents.location

import android.Manifest
import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.danielecampogiani.emilyevents.R
import kotlinx.android.synthetic.main.fragment_location.*


class LocationFragment : LifecycleFragment() {

    companion object {

        fun newInstance(): LocationFragment {
            val fragment = LocationFragment()
            val args = Bundle(0)
            fragment.arguments = args
            return fragment
        }
    }

    private val REQUEST_LOCATION_PERMISSION_CODE = 1


    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var viewModel: LocationViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission()
        } else {
            observeModel()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            observeModel()
        } else {
            animate("location_error.json")
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSION_CODE)
    }

    private fun observeModel() {
        viewModel.getLocation().observe(this, Observer {
            when (it) {
                is LocationState.Loading -> {
                    val animationFile = "location_loading.json"
                    animate(animationFile)
                }
                is LocationState.Result -> mListener?.onLocationReceived(it)
            }
        })
    }

    private fun animate(animationFile: String) {
        animation_view.setAnimation(animationFile)
        animation_view.playAnimation()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnFragmentInteractionListener {
        fun onLocationReceived(location: LocationState.Result)
    }


}
