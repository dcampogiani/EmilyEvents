package com.danielecampogiani.emilyevents.events

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.danielecampogiani.emilyevents.R
import com.danielecampogiani.network.facebook.FacebookEvent
import com.mindorks.placeholderview.SwipeDecor
import kotlinx.android.synthetic.main.fragment_events.*


class EventsFragment : LifecycleFragment() {

    companion object {
        private val ARG_LATITUDE = "latitude"
        private val ARG_LONGITUDE = "longitude"

        fun newInstance(latitude: String, longitude: String): EventsFragment {
            val fragment = EventsFragment()
            val args = Bundle(2)
            args.putString(ARG_LATITUDE, latitude)
            args.putString(ARG_LONGITUDE, longitude)
            fragment.arguments = args
            return fragment
        }
    }


    private lateinit var latitude: String
    private lateinit var longitude: String

    private lateinit var viewModel: EventsViewModel


    private var mListener: OnEventSelectedListener? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)
        viewModel.getUILiveData(latitude, longitude).observe(this, Observer {
            when (it) {
                is EventsState.Loading -> {
                    animate("network_loading.json")
                }
                is EventsState.Result -> {
                    bindResult(it.events)
                }
                is EventsState.Error -> {
                    animate("location_error.json")
                }
            }
        })

    }

    private fun bindResult(events: List<FacebookEvent>) {

        swipeView.getBuilder()
                .setDisplayViewCount(3)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f))

        events.forEach {
            val eventCard = EventCard(context, it)
            swipeView.addView(eventCard)
        }

        animation_view.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            latitude = arguments.getString(ARG_LATITUDE)
            longitude = arguments.getString(ARG_LONGITUDE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_events, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnEventSelectedListener) {
            mListener = context as OnEventSelectedListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    private fun animate(animationFile: String) {
        animation_view.setAnimation(animationFile)
        animation_view.playAnimation()
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnEventSelectedListener {
        fun onEventSelected(event: FacebookEvent)
    }

}
