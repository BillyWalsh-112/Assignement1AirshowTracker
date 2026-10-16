package org.setu.AirshowTracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


// Home screen — entry point with two buttons: View Airshows and Add Airshow
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()                                              // Draw behind system bars for full-screen look
        setContentView(R.layout.activity_main)

        // Push content below the status bar and above the nav bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind the two buttons on the home screen
        val viewAirshowsButton = findViewById<Button>(R.id.viewAirshowsButton)
        val addAirshowButton = findViewById<Button>(R.id.addAirshowButton)


        // Navigate to the list screen
        viewAirshowsButton.setOnClickListener {
            startActivity(Intent(this, AirshowListActivity::class.java))
        }


        // Navigate to the Add Airshow screen
        addAirshowButton.setOnClickListener {
            startActivity(Intent(this, AddEditAirshowActivity::class.java))
        }
    }
}
