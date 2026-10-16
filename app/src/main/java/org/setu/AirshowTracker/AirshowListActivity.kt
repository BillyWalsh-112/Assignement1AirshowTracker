package org.setu.AirshowTracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.setu.AirshowTracker.models.Airshow
import androidx.appcompat.widget.SearchView

class AirshowListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AirshowAdapter

    private lateinit var emptyState: View
    private lateinit var emptyAddButton: Button

    private lateinit var searchView: androidx.appcompat.widget.SearchView

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_airshow_list)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbarList)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.airshowsRecyclerView)
        searchView = findViewById(R.id.searchView)

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

        emptyState = findViewById(R.id.emptyState)
        emptyAddButton = findViewById(R.id.emptyAddButton)

        emptyAddButton.setOnClickListener {
            startActivity(Intent(this, AddEditAirshowActivity::class.java))
        }

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean = false

        override fun onQueryTextChange(newText: String?): Boolean {
            val query = newText?.lowercase()?.trim() ?: ""
            val all = AppData.airshows.findAll()

            val filtered = if (query.isEmpty()) {
                all
            } else {
                all.filter {
                    it.title.lowercase().contains(query) ||
                            it.location.lowercase().contains(query)
                }
            }

            adapter.updateAirshows(filtered)
            emptyState.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
            return true
        }
    })
}
    override fun onResume() {
        super.onResume()
        if (::adapter.isInitialized) {
            val list = AppData.airshows.findAll()
            adapter.updateAirshows(list)
            emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
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
