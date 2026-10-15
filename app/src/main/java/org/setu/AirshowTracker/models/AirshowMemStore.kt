package org.setu.AirshowTracker.models

import java.util.concurrent.atomic.AtomicLong

class AirshowMemStore : AirshowStore {
    private val airshows = ArrayList<Airshow>()
    private val lastId = AtomicLong(0L)

    override fun findAll(): List<Airshow> {
        return airshows
    }

    override fun create(airshow: Airshow) {
        airshow.id = lastId.incrementAndGet()
        airshows.add(airshow)
    }

    override fun update(airshow: Airshow): Boolean {
        val found = findOne(airshow.id)
        return if (found != null) {
            found.title = airshow.title
            found.description = airshow.description
            found.location = airshow.location
            found.theme = airshow.theme
            found.aircraft = airshow.aircraft
            true
        } else {
            false
        }
    }

    override fun delete(id: Long): Boolean {
        val found = findOne(id)
        return if (found != null) {
            airshows.remove(found)
            true
        } else {
            false
        }
    }

    override fun findOne(id: Long): Airshow? {
        return airshows.find { it.id == id }
    }
}
