package org.setu.AirshowTracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.setu.AirshowTracker.models.Airshow

class AddEditAirshowActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var locationInput: EditText
    private lateinit var themeInput: EditText
    private lateinit var aircraftInput: EditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_edit_airshow)

        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        locationInput = findViewById(R.id.locationInput)
        themeInput = findViewById(R.id.themeInput)
        aircraftInput = findViewById(R.id.aircraftInput)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarAdd)
        setSupportActionBar(toolbar)

        editingId = intent.getLongExtra("id", -1L).takeIf { it != -1L }
        supportActionBar?.title = if (editingId == null) "Add Airshow" else "Edit Airshow"

        if (editingId != null) {
            loadExistingAirshow(editingId!!)
        }

        saveButton.setOnClickListener {
            saveAirshow()
        }

    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_airshow, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
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
            Toast.makeText(
                this,
                "Airshow not found",
                Toast.LENGTH_SHORT
            ).show()

            finish()
            return
        }

        titleInput.setText(airshow.title)
        descriptionInput.setText(airshow.description)
        locationInput.setText(airshow.location)
        themeInput.setText(airshow.theme)
        aircraftInput.setText(airshow.aircraft)
    }

    private fun saveAirshow() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val location = locationInput.text.toString().trim()
        val theme = themeInput.text.toString().trim()
        val aircraft = aircraftInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Title is required"
            return
        }

        if (editingId == null) {
            val airshow = Airshow(
                title = title,
                description = description,
                location = location,
                theme = theme,
                aircraft = aircraft
            )

            AppData.airshows.create(airshow)

            Toast.makeText(
                this,
                "Airshow created",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val airshow = Airshow(
                id = editingId!!,
                title = title,
                description = description,
                location = location,
                theme = theme,
                aircraft = aircraft
            )

            AppData.airshows.update(airshow)

            Toast.makeText(
                this,
                "Airshow updated",
                Toast.LENGTH_SHORT
            ).show()
        }
        finish()
    }
}
