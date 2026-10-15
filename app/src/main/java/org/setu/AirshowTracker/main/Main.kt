package org.setu.AirshowTracker.main

import org.setu.AirshowTracker.models.Airshow
import org.setu.AirshowTracker.models.AirshowMemStore
import org.setu.AirshowTracker.models.AirshowStore

val store: AirshowStore = AirshowMemStore()

fun main() {
    println("=== AirshowTracker Console App ===")
    var input: Int
    do {
        input = menu()
        when (input) {
            1 -> addAirshow()
            2 -> listAirshows()
            3 -> updateAirshow()
            4 -> deleteAirshow()
            5 -> searchAirshow()
            0 -> println("\nExiting AirshowTracker application. Goodbye!")
            else -> println("\nInvalid option. Please try again.")
        }
    } while (input != 0)
}

fun menu(): Int {
    println("\n----------------------------------")
    println(" MAIN MENU")
    println("----------------------------------")
    println(" 1. Add Airshow")
    println(" 2. List All Airshows")
    println(" 3. Update an Airshow")
    println(" 4. Delete an Airshow")
    println(" 5. Search Airshow by ID")
    println(" 0. Exit")
    print("\nEnter option: ")
    return readlnOrNull()?.toIntOrNull() ?: -1
}

fun addAirshow() {
    println("\n--- Add Airshow ---")
    print("Enter Title: ")
    val title = readlnOrNull()?.trim().orEmpty()
    print("Enter Description: ")
    val description = readlnOrNull()?.trim().orEmpty()
    print("Enter Location: ")
    val location = readlnOrNull()?.trim().orEmpty()
    print("Enter Theme: ")
    val theme = readlnOrNull()?.trim().orEmpty()
    print("Enter Aircraft: ")
    val aircraft = readlnOrNull()?.trim().orEmpty()

    if (title.isNotEmpty()) {
        val airshow = Airshow(title = title, description = description, location = location, theme = theme, aircraft = aircraft)
        store.create(airshow)
        println("Airshow added successfully with ID: ${airshow.id}")
    } else {
        println("Title cannot be empty. Creation cancelled.")
    }
}

fun listAirshows() {
    println("\n--- All Airshows ---")
    val airshows = store.findAll()
    if (airshows.isEmpty()) {
        println("No airshows stored yet.")
    } else {
        airshows.forEach { println("ID: ${it.id} | Title: ${it.title} | Location: ${it.location} | Theme: ${it.theme} | Aircraft: ${it.aircraft} | Description: ${it.description}") }
    }
}

fun updateAirshow() {
    println("\n--- Update Airshow ---")
    listAirshows()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Airshow to update: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null && store.findOne(id) != null) {
        print("Enter New Title: ")
        val title = readlnOrNull()?.trim().orEmpty()
        print("Enter New Description: ")
        val description = readlnOrNull()?.trim().orEmpty()
        print("Enter New Location: ")
        val location = readlnOrNull()?.trim().orEmpty()
        print("Enter New Theme: ")
        val theme = readlnOrNull()?.trim().orEmpty()
        print("Enter New Aircraft: ")
        val aircraft = readlnOrNull()?.trim().orEmpty()

        if (title.isNotEmpty()) {
            val updated = store.update(Airshow(id = id, title = title, description = description, location = location, theme = theme, aircraft = aircraft))
            if (updated) println("Airshow updated successfully.")
        } else {
            println("Title cannot be empty. Update cancelled.")
        }
    } else {
        println("Airshow with ID $id not found.")
    }
}

fun deleteAirshow() {
    println("\n--- Delete Airshow ---")
    listAirshows()
    if (store.findAll().isEmpty()) return

    print("\nEnter ID of Airshow to delete: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val deleted = store.delete(id)
        if (deleted) {
            println("Airshow with ID $id deleted successfully.")
        } else {
            println("Airshow with ID $id not found.")
        }
    } else {
        println("Invalid ID entered.")
    }
}

fun searchAirshow() {
    println("\n--- Search Airshow ---")
    print("Enter ID: ")
    val id = readlnOrNull()?.toLongOrNull()

    if (id != null) {
        val airshow = store.findOne(id)
        if (airshow != null) {
            println("Found: ID: ${airshow.id} | Title: ${airshow.title} | Location: ${airshow.location} | Theme: ${airshow.theme} | Aircraft: ${airshow.aircraft} | Description: ${airshow.description}")
        } else {
            println("No airshow found with ID $id.")
        }
    } else {
        println("Invalid ID entered.")
    }
}
