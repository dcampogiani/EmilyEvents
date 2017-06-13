package com.danielecampogiani.emilyevents.events

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.danielecampogiani.emilyevents.R
import com.danielecampogiani.network.facebook.FacebookEvent
import com.mindorks.placeholderview.Animation
import com.mindorks.placeholderview.annotations.*
import com.squareup.picasso.Picasso


@Animate(Animation.ENTER_LEFT_DESC)
@NonReusable
@Layout(R.layout.event_card)
class EventCard {

    @View(R.id.eventCardImage)
    private lateinit var imageView: ImageView

    @View(R.id.eventCardTitle)
    private lateinit var title: TextView

    @View(R.id.eventCardDescription)
    private lateinit var description: TextView

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
        description.text = event.description
    }

}