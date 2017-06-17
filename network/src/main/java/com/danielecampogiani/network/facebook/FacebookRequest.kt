package com.danielecampogiani.network.facebook


data class FacebookRequest(val latitude: String,
                           val longitude: String,
                           val distance: String = "1000",
                           val sort: Sort = Sort.Popularity)

sealed class Sort(val parameter: String) {

    object Time : Sort("time")
    object Distance : Sort("distance")
    object Venue : Sort("venue")
    object Popularity : Sort("popularity")
}