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

class AddEditAirshowActivity : AppCompatActivity() {

    private lateinit var titleInput: TextInputEditText
    private lateinit var descriptionInput: TextInputEditText
    private lateinit var locationInput: TextInputEditText
    private lateinit var dateInput: TextInputEditText
    private lateinit var themeInput: TextInputEditText
    private lateinit var aircraftInput: TextInputEditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_airshow)

        // --- Toolbar ---
        val toolbar = findViewById<Toolbar>(R.id.toolbarAdd)
        setSupportActionBar(toolbar)

        // --- Field bindings ---
        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        locationInput = findViewById(R.id.locationInput)
        dateInput = findViewById(R.id.dateInput)
        themeInput = findViewById(R.id.themeInput)
        aircraftInput = findViewById(R.id.aircraftInput)

        val saveButton = findViewById<Button>(R.id.saveButton)

        // --- Date Picker Dialog ---
        dateInput.setOnClickListener {
            showDatePicker()
        }

        // --- Edit mode? ---
        editingId = intent.getLongExtra("id", -1L).takeIf { it != -1L }
        supportActionBar?.title = if (editingId == null) "Add Airshow" else "Edit Airshow"

        if (editingId != null) {
            loadExistingAirshow(editingId!!)
        }

        saveButton.setOnClickListener {
            saveAirshow()
        }
    }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_airshow, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadExistingAirshow(id: Long) {
        val airshow = AppData.airshows.findOne(id)

        if (airshow == null) {
            Toast.makeText(this, "Airshow not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        titleInput.setText(airshow.title)
        descriptionInput.setText(airshow.description)
        locationInput.setText(airshow.location)
        dateInput.setText(airshow.date)
        themeInput.setText(airshow.theme)
        aircraftInput.setText(airshow.aircraft)
    }

    private fun saveAirshow() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val location = locationInput.text.toString().trim()
        val date = dateInput.text.toString().trim()
        val theme = themeInput.text.toString().trim()
        val aircraft = aircraftInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Title is required"
            return
        }

        if (editingId == null) {
            // New airshow
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
            // Editing existing airshow
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
