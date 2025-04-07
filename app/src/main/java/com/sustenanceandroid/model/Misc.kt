package com.sustenanceandroid.model

import com.google.gson.annotations.SerializedName

abstract class Misc

class EventSection : Misc() {
    var events: List<Event>? = null
}

class Event {
    @SerializedName("image_url")
    var imageUrl: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("date")
    var date: String? = null

    @SerializedName("total_spots")
    var totalSpots: Int? = null

    @SerializedName("remaining_spots")
    var remainingSpots: Int? = null
}
