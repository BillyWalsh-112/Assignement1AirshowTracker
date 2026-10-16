package org.setu.AirshowTracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.setu.AirshowTracker.models.Airshow


// Binds a list of Airshow objects to the RecyclerView in the list screen
class AirshowAdapter(
    private var airshows: List<Airshow>,
    private val onEdit: (Airshow) -> Unit,
    private val onDelete: (Airshow) -> Unit
) : RecyclerView.Adapter<AirshowAdapter.AirshowViewHolder>() {


    // Holds references to the views in each row (avoids repeated findViewById)
    class AirshowViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val titleText: TextView =
            itemView.findViewById(R.id.titleText)

        val descriptionText: TextView =
            itemView.findViewById(R.id.descriptionText)

        val locationText: TextView =
            itemView.findViewById(R.id.locationText)

        val dateText: TextView =
            itemView.findViewById(R.id.dateText)

        val themeText: TextView =
            itemView.findViewById(R.id.themeText)

        val aircraftText: TextView =
            itemView.findViewById(R.id.aircraftText)

        val editButton: Button =
            itemView.findViewById(R.id.editButton)

        val deleteButton: Button =
            itemView.findViewById(R.id.deleteButton)
    }
    // Inflates the row layout (item_airshow.xml) and returns a new ViewHolder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AirshowViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_airshow,
                parent,
                false
            )

        return AirshowViewHolder(view)
    }

    // Fills each row with data from the corresponding Airshow object
    override fun onBindViewHolder(
        holder: AirshowViewHolder,
        position: Int
    ) {

        val airshow = airshows[position]
// Set the main title with ID prefix
        holder.titleText.text =
            "${airshow.id}: ${airshow.title}"

        // Set other fields, hiding empty ones
        holder.descriptionText.text =
            airshow.description

        holder.locationText.text =
            if (airshow.location.isNotEmpty()) "Location: ${airshow.location}" else ""

        holder.dateText.text =
            if (airshow.date.isNotEmpty()) "Date: ${airshow.date}" else ""

        holder.themeText.text =
            if (airshow.theme.isNotEmpty()) "Theme: ${airshow.theme}" else ""

        holder.aircraftText.text =
            if (airshow.aircraft.isNotEmpty()) "Aircraft: ${airshow.aircraft}" else ""

        // Wire up Edit and Delete button taps to the callbacks
        holder.editButton.setOnClickListener {
            onEdit(airshow)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(airshow)
        }
    }
    // Tells the RecyclerView how many rows to display
    override fun getItemCount(): Int {
        return airshows.size
    }
    // Replaces the current list with a new one and refreshes the UI
    fun updateAirshows(newAirshows: List<Airshow>) {
        airshows = newAirshows
        notifyDataSetChanged()
    }
}
