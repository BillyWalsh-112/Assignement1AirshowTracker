package org.setu.AirshowTracker.models


data class Airshow(
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var location: String = "",
    var theme: String = "",
    var aircraft: String = ""
)
