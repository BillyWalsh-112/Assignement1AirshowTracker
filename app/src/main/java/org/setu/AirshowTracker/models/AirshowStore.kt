package org.setu.AirshowTracker.models

interface AirshowStore {

    // Returns the full list of all airshows
    fun findAll(): List<Airshow>

    // Adds a new airshow to the store
    fun create(airshow: Airshow)

    // Updates an existing airshow. Returns true if found and updated
    fun update(airshow: Airshow): Boolean

    // Deletes an airshow by its ID. Returns true if found and deleted
    fun delete(id: Long): Boolean

    // Returns a single airshow by its ID, or null if not found
    fun findOne(id: Long): Airshow?
}
