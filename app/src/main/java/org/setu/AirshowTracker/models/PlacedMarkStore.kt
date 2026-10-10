package org.setu.AirshowTracker.models

interface PlacedMarkStore {
    fun findAll(): List<PlacedMark>
    fun create(mark: PlacedMark)
    fun update(mark: PlacedMark): Boolean
    fun delete(id: Long): Boolean
    fun findOne(id: Long): PlacedMark?
}
