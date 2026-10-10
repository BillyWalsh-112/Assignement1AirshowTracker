package org.setu.AirshowTracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val viewAirshowsButton = findViewById<Button>(R.id.viewAirshowsButton)
        val addAirshowButton = findViewById<Button>(R.id.addAirshowButton)

        viewAirshowsButton.setOnClickListener {
            startActivity(Intent(this, AirshowListActivity::class.java))
        }

        addAirshowButton.setOnClickListener {
            startActivity(Intent(this, AddEditAirshowActivity::class.java))
        }
    }
}
