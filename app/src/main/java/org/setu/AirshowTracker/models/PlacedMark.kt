package org.setu.AirshowTracker.models

/**
 * Data class representing a single PlacedMark item.
 * Kotlin automatically generates toString(), equals(), hashCode(), and copy().
 */
data class PlacedMark(
    var id: Long = 0L,
    var title: String = "",
    var description: String = "",
    var x: Double = 0.0,
    var y: Double = 0.0
)
