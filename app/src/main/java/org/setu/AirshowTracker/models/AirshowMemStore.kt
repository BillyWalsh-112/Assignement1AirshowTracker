package org.setu.AirshowTracker.models

import java.util.concurrent.atomic.AtomicLong


class AirshowMemStore : AirshowStore {


    // Holds all airshows in memory
    private val airshows = ArrayList<Airshow>()
    private val lastId = AtomicLong(0L)



    // Returns the full list of stored airshows

    override fun findAll(): List<Airshow> {
        return airshows
    }
    // Assigns a new unique ID and adds the airshow to the list
    override fun create(airshow: Airshow) {
        airshow.id = lastId.incrementAndGet()
        airshows.add(airshow)
    }
    // Finds an airshow by ID and updates its fields. Returns true if found.
    override fun update(airshow: Airshow): Boolean {
        val found = findOne(airshow.id)
        return if (found != null) {
            found.title = airshow.title
            found.description = airshow.description
            found.location = airshow.location
            found.date = airshow.date
            found.theme = airshow.theme
            found.aircraft = airshow.aircraft
            true
        } else {
            false
        }
    }
    // Removes an airshow by ID. Returns true if it existed.
    override fun delete(id: Long): Boolean {
        val found = findOne(id)
        return if (found != null) {
            airshows.remove(found)
            true
        } else {
            false
        }
    }
    // Finds and returns a single airshow by its ID, or null if not found
    override fun findOne(id: Long): Airshow? {
        return airshows.find { it.id == id }
    }
}
