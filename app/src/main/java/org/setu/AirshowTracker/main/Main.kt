package org.setu.AirshowTracker.main

import org.setu.AirshowTracker.models.PlacedMarkMemStore
import org.setu.AirshowTracker.models.PlacedMarkStore
import org.setu.AirshowTracker.models.PlacedMark

val store: PlacedMarkStore = PlacedMarkMemStore()

fun main() {
    println("=== AirshowTracker Console App ===")
    var input: Int
    do {
        input = menu()
        when (input) {
            1 -> addPlacedMark()
            2 -> listPlacedMarks()
            3 -> updatePlacedMark()
            4 -> deletePlacedMark()
            5 -> searchPlacedMark()
            0 -> println("\nExiting AirshowTracker application. Goodbye!")
            else -> println("\nInvalid option. Please try again.")
        }
    } while (input != 0)
}

fun menu(): Int {
    println("\n----------------------------------")
    println(" MAIN MENU")
    println("----------------------------------")
    println(" 1. Add Placed Mark")
    println(" 2. List All Placed Marks")
    println(" 3. Update a Placed Mark")
    println(" 4. Delete a Placed Mark")
    println(" 5. Search Placed Mark by ID")
    println(" 0. Exit")
    print("\nEnter option: ")
    return readlnOrNull()?.toIntOrNull() ?: -1
}

fun addPlacedMark() {
    println("\n--- Add Placed Mark ---")
    print("Enter Title: ")
    val title = readlnOrNull()?.trim().orEmpty()
    print("Enter Description: ")
    val description = readlnOrNull()?.trim().orEmpty()

    if (title.isNotEmpty()) {
        val mark = PlacedMark(title = title, description = description)
        store.create(mark)
        println("Placed Mark added successfully with ID: ${mark.id}")
    } else {
        println("Title cannot be empty. Creation cancelled.")
    }
}

fun listPlacedMarks() {
    println("\n--- All Placed Marks ---")
    val marks = store.findAll()
    if (marks.isEmpty()) {
        println("No placed marks stored yet.")
    } else {
        marks.forEach { println("ID: ${it.id} | Title: ${it.title} | Description: ${it.description}") }
    }
}

fun updatePlacedMark() {
    println("\n--- Update Placed Mark ---")
    listPlacedMarks()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Placed Mark to update: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null && store.findOne(id) != null) {
        print("Enter New Title: ")
        val title = readlnOrNull()?.trim().orEmpty()
        print("Enter New Description: ")
        val description = readlnOrNull()?.trim().orEmpty()

        if (title.isNotEmpty()) {
            val updated = store.update(PlacedMark(id = id, title = title, description = description))
            if (updated) println("Placed Mark updated successfully.")
        } else {
            println("Title cannot be empty. Update cancelled.")
        }
    } else {
        println("Placed Mark with ID $id not found.")
    }
}

fun deletePlacedMark() {
    println("\n--- Delete Placed Mark ---")
    listPlacedMarks()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Placed Mark to delete: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val deleted = store.delete(id)
        if (deleted) {
            println("Placed Mark with ID $id deleted successfully.")
        } else {
            println("Placed Mark with ID $id not found.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun searchPlacedMark() {
    println("\n--- Search Placed Mark ---")
    print("Enter ID: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val mark = store.findOne(id)
        if (mark != null) {
            println("Found: ID: ${mark.id} | Title: ${mark.title} | Description: ${mark.description}")
        } else {
            println("No placed mark found with ID $id.")
        }
    } else {
        println("Invalid ID entered.")
    }
}
