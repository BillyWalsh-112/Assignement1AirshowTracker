package org.setu.AirshowTracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.setu.AirshowTracker.models.Airshow

class AirshowAdapter(
    private var airshows: List<Airshow>,
    private val onEdit: (Airshow) -> Unit,
    private val onDelete: (Airshow) -> Unit
) : RecyclerView.Adapter<AirshowAdapter.AirshowViewHolder>() {

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

    override fun onBindViewHolder(
        holder: AirshowViewHolder,
        position: Int
    ) {

        val airshow = airshows[position]

        holder.titleText.text =
            "${airshow.id}: ${airshow.title}"

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

        holder.editButton.setOnClickListener {
            onEdit(airshow)
        }

        holder.deleteButton.setOnClickListener {
            onDelete(airshow)
        }
    }

    override fun getItemCount(): Int {
        return airshows.size
    }

    fun updateAirshows(newAirshows: List<Airshow>) {
        airshows = newAirshows
        notifyDataSetChanged()
    }
}
