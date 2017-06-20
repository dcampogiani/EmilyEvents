package com.danielecampogiani.emilyevents.events

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import com.danielecampogiani.emilyevents.R
import com.danielecampogiani.network.facebook.FacebookEvent
import com.danielecampogiani.network.facebook.Sort
import com.danielecampogiani.network.facebook.Time
import com.mindorks.placeholderview.SwipeDecor
import kotlinx.android.synthetic.main.fragment_events.*
import utils.setFixedOnItemSelectedListener


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
                    showLoading()
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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sheetBehavior = BottomSheetBehavior.from(bottom_refine)

        fab.setOnClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }

        swipeView.builder
                .setDisplayViewCount(3)
                .setSwipeDecor(SwipeDecor()
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f))

        val sortAdapter = ArrayAdapter.createFromResource(context, R.array.sort_by_array, android.R.layout.simple_spinner_item)
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sort_spinner.adapter = sortAdapter
        sort_spinner.setFixedOnItemSelectedListener {

            var sort: Sort = Sort.Popularity

            if (it == 0) {
                sort = Sort.Popularity
            } else if (it == 1) {
                sort = Sort.Time
            } else if (it == 2) {
                sort = Sort.Distance
            } else if (it == 3) {
                sort = Sort.Venue
            }

            viewModel.changeSort(sort)
        }

        val timeAdapter = ArrayAdapter.createFromResource(context, R.array.time_refine_array, android.R.layout.simple_spinner_item)
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        time_spinner.adapter = timeAdapter
        time_spinner.setFixedOnItemSelectedListener {
            val time: Time

            if (it == 1) {
                time = Time.today()
            } else if (it == 2) {
                time = Time.tomorrow()
            } else if (it == 3) {
                time = Time.thisWeek()
            } else if (it == 4) {
                time = Time.thisWeekEnd()
            } else if (it == 5) {
                time = Time.nextWeek()
            } else {
                time = Time.all()
            }


            viewModel.changeTime(time)
        }



        distance_seek.max = 10000
        distance_seek.progress = 1000
        distance_text.text = "1000"
        distance_seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                distance_text.text = progress.toString()

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                viewModel.changeDistance(distance_seek.progress)
            }
        })
    }

    private fun showLoading() {
        TransitionManager.beginDelayedTransition(root)
        animate("network_loading.json")
        animation_view.visibility = View.VISIBLE
        swipeView.visibility = View.INVISIBLE
    }

    private fun bindResult(events: List<FacebookEvent>) {
        swipeView.removeAllViews()
        TransitionManager.beginDelayedTransition(root)

        events.forEach {
            val eventCard = EventCard(context, it)
            swipeView.addView(eventCard)
        }

        swipeView.visibility = View.VISIBLE
        animation_view.visibility = View.INVISIBLE
        fab.visibility = View.VISIBLE
        bottom_refine.visibility = View.VISIBLE
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
