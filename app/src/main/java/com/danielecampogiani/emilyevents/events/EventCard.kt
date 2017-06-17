package com.danielecampogiani.emilyevents.events

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.danielecampogiani.emilyevents.R
import com.danielecampogiani.network.facebook.FacebookEvent
import com.mindorks.placeholderview.Animation
import com.mindorks.placeholderview.annotations.*
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


@Animate(Animation.ENTER_LEFT_DESC)
@NonReusable
@Layout(R.layout.event_card)
class EventCard {

    @View(R.id.eventCardImage)
    private lateinit var imageView: ImageView

    @View(R.id.eventCardTitle)
    private lateinit var title: TextView

    @View(R.id.eventCardDate)
    private lateinit var date: TextView

    @View(R.id.eventCardLocation)
    private lateinit var location: TextView

    private var context: Context
    private var event: FacebookEvent

    constructor(context: Context, event: FacebookEvent) {
        this.context = context
        this.event = event
    }

    @Resolve
    private fun onResolved() {
        Picasso.with(context).load(event.profilePicture).into(imageView)
        title.text = event.name
        val df = SimpleDateFormat("dd/MM")
        val formattedDate = df.format(event.startTime)
        date.text = formattedDate
        val venue = event.venue
        location.text = "${venue.name} - ${venue.location.street}"
    }

}