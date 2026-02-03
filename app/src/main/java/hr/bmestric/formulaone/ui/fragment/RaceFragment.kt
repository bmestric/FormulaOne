package hr.bmestric.formulaone.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import hr.bmestric.formulaone.R
import hr.bmestric.formulaone.databinding.FragmentRacesBinding
import hr.bmestric.formulaone.framework.RepositoryProvider
import hr.bmestric.formulaone.framework.setupYearSpinner
import hr.bmestric.formulaone.ui.adapter.RaceAdapter
import kotlinx.coroutines.launch

class RaceFragment : Fragment() {

    private lateinit var binding: FragmentRacesBinding
    private var selectedYear: Int = 2025

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRacesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupYearSpinner(binding.spinnerYear) { year ->
            selectedYear = year
            loadRaces(year)
        }
    }


    fun loadRaces(year: Int) {
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE

                val sessions = RepositoryProvider.sessionRepository.getSessionsByYear(year)
                val meetings = RepositoryProvider.meetingRepository.getMeetingsByYear(year)

                val meetingImages = meetings.associate { it.meetingKey to it.circuitImage }

                val raceSessions = sessions.filter { it.sessionType.equals("race", ignoreCase = true) }

                val adapter = RaceAdapter(raceSessions, meetingImages) { race ->
                    val meeting = meetings.find { it.meetingKey == race.meetingKey }

                    val bundle = Bundle().apply {
                        putInt("meetingKey", race.meetingKey)
                        putInt("sessionKey", race.sessionKey)
                        putString("raceName", race.name)
                        putString("raceCountry", race.country)
                        putString("raceCircuit", race.circuit)
                        putString("dateStart", race.dateStart)
                        putString("circuitImage", meetingImages[race.meetingKey])
                        putString("circuitInfoUrl", meeting?.circuitInfoUrl)
                    }

                    try {
                        findNavController().navigate(
                            R.id.action_menuRace_to_raceInfoFragment,
                            bundle
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                // Use GridLayoutManager in landscape, LinearLayoutManager in portrait
                val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1
                binding.rvRaces.layoutManager = if (spanCount > 1) {
                    GridLayoutManager(requireContext(), spanCount)
                } else {
                    LinearLayoutManager(requireContext())
                }
                binding.rvRaces.adapter = adapter

                binding.progressBar.visibility = View.GONE
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
