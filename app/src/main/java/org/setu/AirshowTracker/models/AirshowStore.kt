package org.setu.AirshowTracker.models

interface AirshowStore {
    fun findAll(): List<Airshow>
    fun create(airshow: Airshow)
    fun update(airshow: Airshow): Boolean
    fun delete(id: Long): Boolean
    fun findOne(id: Long): Airshow?
}
