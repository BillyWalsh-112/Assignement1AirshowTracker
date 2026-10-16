package org.setu.AirshowTracker

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import org.setu.AirshowTracker.models.Airshow
import java.util.Calendar
import java.util.Locale


// Handles both adding a new airshow and editing an existing one
class AddEditAirshowActivity : AppCompatActivity() {


    // Input field references
    private lateinit var titleInput: TextInputEditText
    private lateinit var descriptionInput: TextInputEditText
    private lateinit var locationInput: TextInputEditText
    private lateinit var dateInput: TextInputEditText
    private lateinit var themeInput: TextInputEditText
    private lateinit var aircraftInput: TextInputEditText

    // Holds the ID if editing an existing airshow, null if creating a new one
    private var editingId: Long? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_airshow)

        // Set up the toolbar as the app bar
        val toolbar = findViewById<Toolbar>(R.id.toolbarAdd)
        setSupportActionBar(toolbar)

        // Bind all  fields to their XML views
        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        locationInput = findViewById(R.id.locationInput)
        dateInput = findViewById(R.id.dateInput)
        themeInput = findViewById(R.id.themeInput)
        aircraftInput = findViewById(R.id.aircraftInput)

        val saveButton = findViewById<Button>(R.id.saveButton)

        // Open the date picker when the date field is tapped
        dateInput.setOnClickListener {
            showDatePicker()
        }

        // Check if we're editing (an ID was passed in) or adding (no ID)
        editingId = intent.getLongExtra("id", -1L).takeIf { it != -1L }
        supportActionBar?.title = if (editingId == null) "Add Airshow" else "Edit Airshow"

        // If editing, load the existing airshow's data into the fields
        if (editingId != null) {
            loadExistingAirshow(editingId!!)
        }

        // Save button triggers the create/update logic
        saveButton.setOnClickListener {
            saveAirshow()
        }
    }


    // Shows a calendar dialog and writes the chosen date into the date field
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format(
                    Locale.getDefault(),
                    "%02d/%02d/%04d",
                    selectedDay,
                    selectedMonth + 1,
                    selectedYear
                )
                dateInput.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }


    // Inflates the toolbar menu (contains the Cancel action)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_airshow, menu)
        return super.onCreateOptionsMenu(menu)
    }


    // Handles the Cancel button tap — closes the screen without saving
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // Loads an existing airshow's data into the input fields (edit mode)
    private fun loadExistingAirshow(id: Long) {
        val airshow = AppData.airshows.findOne(id)

        // If the airshow doesn't exist, close the screen
        if (airshow == null) {
            Toast.makeText(this, "Airshow not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }


        // Populate the fields with the existing data
        titleInput.setText(airshow.title)
        descriptionInput.setText(airshow.description)
        locationInput.setText(airshow.location)
        dateInput.setText(airshow.date)
        themeInput.setText(airshow.theme)
        aircraftInput.setText(airshow.aircraft)
    }


    // Reads input fields and either creates a new airshow or updates the existing one
    private fun saveAirshow() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val location = locationInput.text.toString().trim()
        val date = dateInput.text.toString().trim()
        val theme = themeInput.text.toString().trim()
        val aircraft = aircraftInput.text.toString().trim()

        // Validate: title is required
        if (title.isEmpty()) {
            titleInput.error = "Title is required"
            return
        }

        if (editingId == null) {
            // Create mode — add a new airshow to the store
            AppData.airshows.create(
                Airshow(
                    title = title,
                    description = description,
                    location = location,
                    date = date,
                    theme = theme,
                    aircraft = aircraft
                )
            )
            Toast.makeText(this, "Airshow created", Toast.LENGTH_SHORT).show()
        } else {
            // Edit mode — update the existing airshow in the store
            AppData.airshows.update(
                Airshow(
                    id = editingId!!,
                    title = title,
                    description = description,
                    location = location,
                    date = date,
                    theme = theme,
                    aircraft = aircraft
                )
            )
            Toast.makeText(this, "Airshow updated", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}
