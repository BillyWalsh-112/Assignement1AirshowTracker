package org.setu.AirshowTracker.models

// Data model representing a single airshow event
data class Airshow(
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var location: String = "",
    var date: String = "",
    var theme: String = "",
    var aircraft: String = ""
)
