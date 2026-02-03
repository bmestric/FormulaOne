package hr.bmestric.formulaone.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.bmestric.formulaone.R
import hr.bmestric.formulaone.domain.model.Driver
import hr.bmestric.formulaone.domain.model.SessionResult

class RaceResultsAdapter(
    private val results: List<SessionResult>,
    private val drivers: List<Driver>
) : RecyclerView.Adapter<RaceResultsAdapter.RaceResultViewHolder>() {

    class RaceResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewTeamColor: View = itemView.findViewById(R.id.viewTeamColor)
        val tvPosition: TextView = itemView.findViewById(R.id.tvPosition)
        val tvDriverNumber: TextView = itemView.findViewById(R.id.tvDriverNumber)
        val tvDriverName: TextView = itemView.findViewById(R.id.tvDriverName)
        val tvLaps: TextView = itemView.findViewById(R.id.tvLaps)
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceResultViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.race_result, parent, false)
        return RaceResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: RaceResultViewHolder, position: Int) {
        val result = results[position]
        val driver = drivers.find { it.driverNumber == result.driverNumber }

        holder.tvPosition.text = result.position.toString()
        holder.tvDriverNumber.text = "#${result.driverNumber}"
        holder.tvDriverName.text = driver?.broadcastName ?: "Driver #${result.driverNumber}"
        holder.tvLaps.text = "${result.numberOfLaps} laps"

        // Show time or DNF status
        holder.tvTime.text = when {
            result.dnf -> "DNF"
            result.position == 1 -> result.lapDuration?.let { formatLapTime(it) } ?: "-"
            else -> result.gapToLeader ?: "-"  // Already formatted string (e.g., "+1 LAP", "+5.234")
        }

        // Set team color stripe
        driver?.teamColour?.let { colorHex ->
            try {
                val color = Color.parseColor("#$colorHex")
                holder.viewTeamColor.setBackgroundColor(color)
            } catch (e: IllegalArgumentException) {
                // If color parsing fails, use default gray
                holder.viewTeamColor.setBackgroundColor(Color.GRAY)
            }
        } ?: run {
            // No driver found, use default gray
            holder.viewTeamColor.setBackgroundColor(Color.GRAY)
        }
    }

    override fun getItemCount() = results.size

    private fun formatLapTime(seconds: Double): String {
        val minutes = (seconds / 60).toInt()
        val secs = seconds % 60
        return String.format("%d:%06.3f", minutes, secs)
    }
}