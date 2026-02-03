package hr.bmestric.formulaone.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.bmestric.formulaone.R
import hr.bmestric.formulaone.domain.model.Driver

class DriverAdapter(private val drivers: List<Driver>) : RecyclerView.Adapter<DriverAdapter.DriverViewHolder>() {

    class DriverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivDriverHeadshot: ImageView = itemView.findViewById(R.id.ivDriverHeadshot)
        val viewTeamColor: View = itemView.findViewById(R.id.viewTeamColor)
        val tvDriverNumber: TextView = itemView.findViewById(R.id.tvDriverNumber)
        val tvBroadcastName: TextView = itemView.findViewById(R.id.tvBroadcastName)
        val tvFullName: TextView = itemView.findViewById(R.id.tvFullName)
        val tvTeamName: TextView = itemView.findViewById(R.id.tvTeamName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriverViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.driver, parent, false)
        return DriverViewHolder(view)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        val driver = drivers[position]

        if (!driver.headshotUrl.isNullOrEmpty()) {
            Picasso.get()
                .load(driver.headshotUrl)
                .placeholder(R.drawable.driver_helmet)
                .error(R.drawable.driver_helmet)
                .into(holder.ivDriverHeadshot)
        } else {
            holder.ivDriverHeadshot.setImageResource(R.drawable.driver_helmet)
        }

        holder.tvDriverNumber.text = "#${driver.driverNumber}"

        holder.tvBroadcastName.text = driver.broadcastName

        holder.tvFullName.text = driver.fullName

        holder.tvTeamName.text = driver.teamName

        try {
            val color = Color.parseColor("#${driver.teamColour}")
            holder.viewTeamColor.setBackgroundColor(color)
        } catch (_: Exception) {
            holder.viewTeamColor.setBackgroundColor(Color.GRAY)
        }
    }

    override fun getItemCount(): Int = drivers.size
}

