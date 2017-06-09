package com.danielecampogiani.emilyevents.location

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.danielecampogiani.emilyevents.R
import kotlinx.android.synthetic.main.fragment_location.*


class LocationFragment : LifecycleFragment() {

    private var mListener: OnFragmentInteractionListener? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        viewModel.getLocation().observe(this, Observer {
            when (it) {
                is LocationState.Loading -> {
                    animation_view.setAnimation("location_loading.json")
                    animation_view.playAnimation()
                }
                is LocationState.Result -> Toast.makeText(context, "$it.latitude $it.longitude", Toast.LENGTH_LONG).show()
                is LocationState.Error -> {
                    animation_view.setAnimation("location_error.json")
                    animation_view.playAnimation()
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnFragmentInteractionListener {
        fun onLocationReceived(location: LocationState.Result)
    }

    companion object {

        fun newInstance(): LocationFragment {
            val fragment = LocationFragment()
            val args = Bundle(0)
            fragment.arguments = args
            return fragment
        }
    }
}
