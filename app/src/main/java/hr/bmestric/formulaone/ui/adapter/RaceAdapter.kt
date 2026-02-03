package hr.bmestric.formulaone.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.bmestric.formulaone.R
import hr.bmestric.formulaone.domain.model.Session
import com.squareup.picasso.Picasso
import hr.bmestric.formulaone.framework.toFormattedRaceDate

class RaceAdapter(
    private val sessions: List<Session>,
    private val meetingImages: Map<Int, String>,
    private val onRaceClick: (Session) -> Unit
) : RecyclerView.Adapter<RaceAdapter.RaceViewHolder>() {

    class RaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivCircuitImage: ImageView = itemView.findViewById(R.id.ivCircuitImage)
        val tvRaceName: TextView = itemView.findViewById(R.id.tvRaceName)
        val tvRaceLocation: TextView = itemView.findViewById(R.id.tvRaceLocation)
        val tvRaceDate: TextView = itemView.findViewById(R.id.tvRaceDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.race, parent, false)
        return RaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: RaceViewHolder, position: Int) {
        val session = sessions[position]
        holder.tvRaceName.text = session.name
        holder.tvRaceLocation.text = "${session.country} • ${session.circuit}"
        holder.tvRaceDate.text = session.dateStart.toFormattedRaceDate()

        val circuitImageUrl = meetingImages[session.meetingKey]
        Picasso.get().load(circuitImageUrl).into(holder.ivCircuitImage)

        holder.itemView.setOnClickListener {
            onRaceClick(session)
        }

    }

    override fun getItemCount(): Int = sessions.size
}
