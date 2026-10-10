package org.setu.AirshowTracker.models

import java.util.concurrent.atomic.AtomicLong

class PlacedMarkMemStore : PlacedMarkStore {
    private val marks = ArrayList<PlacedMark>()
    private val lastId = AtomicLong(0L)

    override fun findAll(): List<PlacedMark> {
        return marks
    }

    override fun create(mark: PlacedMark) {
        mark.id = lastId.incrementAndGet()
        marks.add(mark)
    }

    override fun update(mark: PlacedMark): Boolean {
        val foundMark = findOne(mark.id)
        return if (foundMark != null) {
            foundMark.title = mark.title
            foundMark.description = mark.description
            foundMark.x = mark.x
            foundMark.y = mark.y
            true
        } else {
            false
        }
    }

    override fun delete(id: Long): Boolean {
        val foundMark = findOne(id)
        return if (foundMark != null) {
            marks.remove(foundMark)
            true
        } else {
            false
        }
    }

    override fun findOne(id: Long): PlacedMark? {
        return marks.find { (pId) -> pId == id }
    }
}
