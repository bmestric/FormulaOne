package hr.bmestric.formulaone.ui.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.bmestric.formulaone.R
import hr.bmestric.formulaone.domain.model.Driver
import hr.bmestric.formulaone.domain.model.StartingGrid


class StartingGridAdapter(
    private val startingGrid: List<StartingGrid>,
    private val drivers: List<Driver>
) : RecyclerView.Adapter<StartingGridAdapter.StartingGridViewHolder>() {

    class StartingGridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewTeamColor: View = itemView.findViewById(R.id.viewTeamColor)
        val tvPosition: TextView = itemView.findViewById(R.id.tvPosition)
        val tvDriverNumber: TextView = itemView.findViewById(R.id.tvDriverNumber)
        val tvDriverName: TextView = itemView.findViewById(R.id.tvDriverName)
        val tvLapTime: TextView = itemView.findViewById(R.id.tvLaps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartingGridViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.starting_grid, parent, false)
        return StartingGridViewHolder(view)
    }

    override fun onBindViewHolder(holder: StartingGridViewHolder, position: Int) {
        val gridPosition = startingGrid[position]
        val driver = drivers.find { it.driverNumber == gridPosition.driverNumber }

        holder.tvPosition.text = gridPosition.position.toString()
        holder.tvDriverNumber.text = "#${gridPosition.driverNumber}"
        holder.tvDriverName.text = driver?.broadcastName ?: "Driver #${gridPosition.driverNumber}"

        holder.tvLapTime.text = gridPosition.lapDuration?.let {
            formatLapTime(it)
        } ?: "-"

        driver?.teamColour?.let { colorHex ->
            try {
                val color = Color.parseColor("#$colorHex")
                holder.viewTeamColor.setBackgroundColor(color)
            } catch (e: IllegalArgumentException) {
                holder.viewTeamColor.setBackgroundColor(Color.GRAY)
            }
        } ?: run {
            holder.viewTeamColor.setBackgroundColor(Color.GRAY)
        }
    }

    override fun getItemCount() = startingGrid.size

    private fun formatLapTime(seconds: Double): String {
        val minutes = (seconds / 60).toInt()
        val secs = seconds % 60
        return String.format("%d:%06.3f", minutes, secs)
    }
}