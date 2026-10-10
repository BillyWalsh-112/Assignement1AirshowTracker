package org.setu.AirshowTracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.setu.AirshowTracker.models.Airshow

class AirshowListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AirshowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_airshow_list)

        recyclerView = findViewById(R.id.airshowsRecyclerView)

        adapter = AirshowAdapter(
            airshows = AppData.airshows.findAll(),
            onEdit = { airshow ->
                editAirshow(airshow)
            },
            onDelete = { airshow ->
                deleteAirshow(airshow)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val returnButton = findViewById<Button>(R.id.returnButton)
        returnButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        if (::adapter.isInitialized) {
            adapter.updateAirshows(AppData.airshows.findAll())
        }
    }

    private fun editAirshow(airshow: Airshow) {
        val intent = Intent(this, AddEditAirshowActivity::class.java)
        intent.putExtra("id", airshow.id)
        startActivity(intent)
    }

    private fun deleteAirshow(airshow: Airshow) {
        AppData.airshows.delete(airshow.id)
        adapter.updateAirshows(AppData.airshows.findAll())
    }
}
