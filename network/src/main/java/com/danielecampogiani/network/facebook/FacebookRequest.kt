package com.danielecampogiani.network.facebook


data class FacebookRequest(val latitude: String,
                           val longitude: String,
                           val distance: String = "1000",
                           val sort: Sort = Sort.Popularity,
                           val time: Time? = null)

sealed class Sort(val parameter: String) {

    object Time : Sort("time")
    object Distance : Sort("distance")
    object Venue : Sort("venue")
    object Popularity : Sort("popularity")
}

data class Time(var since: String?, val until: String?) {

    companion object {

        fun all(): Time {
            return Time(null, null)
        }

        fun today(): Time {
            val now = DateTime.now()
            val todayStart = now.withTimeAtStartOfDay()
            val tomorrowStart = now.plusDays(1).withTimeAtStartOfDay()
            val startUnix = todayStart.millis / 1000
            val endUnix = tomorrowStart.millis / 1000
            return Time(startUnix.toString(), endUnix.toString())
        }

        fun tomorrow(): Time {
            val tomorrow = DateTime.now().plusDays(1)
            val tomorrowStart = tomorrow.withTimeAtStartOfDay()
            val tomorrowEnd = tomorrow.plusDays(1).withTimeAtStartOfDay()
            val startUnix = tomorrowStart.millis / 1000
            val endUnix = tomorrowEnd.millis / 1000
            return Time(startUnix.toString(), endUnix.toString())
        }

        fun thisWeek(): Time {
            return Time(null, null)
        }

        fun thisWeekEnd(): Time {
            return Time(null, null)
        }

        fun nextWeek(): Time {
            return Time(null, null)
        }
    }
}