package org.setu.AirshowTracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.setu.AirshowTracker.models.Airshow

class AddEditAirshowActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var descriptionInput: EditText
    private lateinit var locationInput: EditText
    private lateinit var xInput: EditText
    private lateinit var yInput: EditText

    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_edit_airshow)

        titleInput = findViewById(R.id.titleInput)
        descriptionInput = findViewById(R.id.descriptionInput)
        locationInput = findViewById(R.id.locationInput)
        xInput = findViewById(R.id.xInput)
        yInput = findViewById(R.id.yInput)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        editingId = intent.getLongExtra("id", -1L).takeIf { it != -1L }

        if (editingId != null) {
            findViewById<TextView>(R.id.formTitle).text = "Edit Airshow"
            loadExistingAirshow(editingId!!)
        }

        saveButton.setOnClickListener {
            saveAirshow()
        }

        cancelButton.setOnClickListener {
            finish()
        }
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
        xInput.setText(airshow.x.toString())
        yInput.setText(airshow.y.toString())
    }

    private fun saveAirshow() {
        val title = titleInput.text.toString().trim()
        val description = descriptionInput.text.toString().trim()
        val location = locationInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Title is required"
            return
        }

        val x = xInput.text.toString().toDoubleOrNull()
        if (x == null) {
            xInput.error = "Enter a valid number for location (X coordinate)"
            return
        }

        val y = yInput.text.toString().toDoubleOrNull()
        if (y == null) {
            yInput.error = "Enter a valid number for location (Y coordinate)"
            return
        }

        if (editingId == null) {
            val airshow = Airshow(
                title = title,
                description = description,
                location = location,
                x = x,
                y = y
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
                x = x,
                y = y
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
