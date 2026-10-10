package org.setu.AirshowTracker.models

/**
 * Data class representing an Airshow item.
 */
data class Airshow(
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var location: String = "",
    var x: Double = 0.0,
    var y: Double = 0.0
)
