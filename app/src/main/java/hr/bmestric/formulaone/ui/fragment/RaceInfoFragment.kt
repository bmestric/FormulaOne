package hr.bmestric.formulaone.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import hr.bmestric.formulaone.R
import hr.bmestric.formulaone.databinding.FragmentRaceInfoBinding
import hr.bmestric.formulaone.framework.RepositoryProvider
import hr.bmestric.formulaone.framework.toFormattedRaceDate
import hr.bmestric.formulaone.ui.adapter.RaceResultsAdapter
import hr.bmestric.formulaone.ui.adapter.StartingGridAdapter
import kotlinx.coroutines.launch

class RaceInfoFragment : Fragment() {
    private lateinit var binding: FragmentRaceInfoBinding
    private var showingStartingGrid = true
    private var sessionKey: Int = 0
    private var meetingKey: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRaceInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { bundle ->
            val raceName = bundle.getString("raceName", "")
            val raceCountry = bundle.getString("raceCountry", "")
            val raceCircuit = bundle.getString("raceCircuit", "")
            val dateStart = bundle.getString("dateStart", "")
            val circuitImage = bundle.getString("circuitImage")
            sessionKey = bundle.getInt("sessionKey", 0)
            meetingKey = bundle.getInt("meetingKey", 0)

            Log.d("RaceInfoFragment", "Race sessionKey: $sessionKey, meetingKey: $meetingKey")

            binding.tvRaceName.text = raceName
            binding.tvRaceLocation.text = "$raceCountry • $raceCircuit"
            binding.tvRaceDate.text = dateStart?.toFormattedRaceDate() ?: dateStart

            if (!circuitImage.isNullOrEmpty()) {
                Picasso.get()
                    .load(circuitImage)
                    .placeholder(R.drawable.default_track)
                    .error(R.drawable.default_track)
                    .into(binding.ivCircuitImage)
            }

            binding.btnToggleView.setOnClickListener {
                showingStartingGrid = !showingStartingGrid
                updateToggleButton()
                loadRaceData()
            }

            updateToggleButton()
            loadRaceData()
        }
    }
    private fun updateToggleButton() {
        if (showingStartingGrid) {
            binding.btnToggleView.text = getString(R.string.show_results)
            binding.tvSectionTitle.text = getString(R.string.starting_grid)
        } else {
            binding.btnToggleView.text = getString(R.string.starting_grid)
            binding.tvSectionTitle.text = getString(R.string.show_results)
        }
    }

    private fun loadRaceData() {
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                binding.rvResults.visibility = View.GONE
                binding.tvEmptyState.visibility = View.GONE

                Log.d("RaceInfoFragment", "Loading data for sessionKey: $sessionKey, showing: ${if (showingStartingGrid) "Starting Grid" else "Results"}")

                if (showingStartingGrid) {
                    loadStartingGrid()
                } else {
                    loadRaceResults()
                }

                binding.progressBar.visibility = View.GONE
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                binding.rvResults.visibility = View.GONE
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.text = "Error loading data"
                Log.e("RaceInfoFragment", "Error loading race data", e)
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun loadStartingGrid() {
        Log.d("RaceInfoFragment", "============ STARTING GRID DEBUG ============")
        Log.d("RaceInfoFragment", "Race session: $sessionKey, Meeting: $meetingKey")

        try {
            Log.d("RaceInfoFragment", "Finding qualifying session for meeting: $meetingKey")
            val qualifyingSessionKey = findQualifyingSessionKey(meetingKey)

            if (qualifyingSessionKey == null) {
                Log.w("RaceInfoFragment", "No qualifying session found for meeting: $meetingKey")
                binding.rvResults.visibility = View.GONE
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.text = "No qualifying session found for this race"
                return
            }

            Log.d("RaceInfoFragment", "Qualifying session found: $qualifyingSessionKey")
            Log.d("RaceInfoFragment", "Fetching starting grid from qualifying session: $qualifyingSessionKey")

            val startingGrid = RepositoryProvider.startingGridRepository.getStartingGrid(qualifyingSessionKey)
            Log.d("RaceInfoFragment", "Starting grid API response size: ${startingGrid.size}")

            if (startingGrid.isNotEmpty()) {
                Log.d("RaceInfoFragment", "First 3 positions: ${startingGrid.take(3)}")
            }

            val drivers = RepositoryProvider.driverRepository.getDriversBySession(qualifyingSessionKey)
            Log.d("RaceInfoFragment", "Drivers for qualifying session size: ${drivers.size}")

            if (drivers.isNotEmpty()) {
                Log.d("RaceInfoFragment", "First 3 drivers: ${drivers.take(3).map { it.broadcastName }}")
            }

            Log.d("RaceInfoFragment", "Starting grid FINAL size: ${startingGrid.size}, drivers size: ${drivers.size}")

            if (startingGrid.isEmpty()) {
                binding.rvResults.visibility = View.GONE
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.text = "No starting grid data available for this race"
                Log.w("RaceInfoFragment", "Starting grid is EMPTY for qualifying session: $qualifyingSessionKey")
            } else {
                binding.rvResults.visibility = View.VISIBLE
                binding.tvEmptyState.visibility = View.GONE
                val adapter = StartingGridAdapter(startingGrid, drivers)
                binding.rvResults.layoutManager = LinearLayoutManager(requireContext())
                binding.rvResults.adapter = adapter
                Log.d("RaceInfoFragment", "Adapter set with ${startingGrid.size} items")
                Log.d("RaceInfoFragment", "RecyclerView adapter item count: ${adapter.itemCount}")
            }
        } catch (e: Exception) {
            Log.e("RaceInfoFragment", "ERROR fetching starting grid: ${e.message}", e)
            binding.rvResults.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.tvEmptyState.text = "Error loading starting grid"
        }
        Log.d("RaceInfoFragment", "============ END STARTING GRID DEBUG ============")
    }

    private suspend fun findQualifyingSessionKey(meetingKey: Int): Int? {
        return try {
            Log.d("RaceInfoFragment", "Querying API: /sessions?meeting_key=$meetingKey")

            val sessions = RepositoryProvider.sessionRepository.getSessionsByMeeting(meetingKey)

            Log.d("RaceInfoFragment", "Found ${sessions.size} sessions for meeting $meetingKey")
            sessions.forEach { session ->
                Log.d("RaceInfoFragment", "  - ${session.sessionType}: sessionKey=${session.sessionKey}")
            }

            val sortedSessions = sessions.sortedBy { it.sessionKey }

            val qualifyingSession = sortedSessions
                .filter { it.sessionType.equals("Qualifying", ignoreCase = true) }
                .lastOrNull { it.sessionKey < sessionKey }

            if (qualifyingSession != null) {
                Log.d("RaceInfoFragment", "Qualifying session found: ${qualifyingSession.sessionKey} (before race ${sessionKey})")
            } else {
                Log.w("RaceInfoFragment", "No qualifying session found before race session $sessionKey")

                val anyQualifying = sessions.find { it.sessionType.equals("Qualifying", ignoreCase = true) }
                if (anyQualifying != null) {
                    Log.d("RaceInfoFragment", "Using fallback qualifying: ${anyQualifying.sessionKey}")
                    return anyQualifying.sessionKey
                }
            }

            qualifyingSession?.sessionKey
        } catch (e: Exception) {
            Log.e("RaceInfoFragment", "Error finding qualifying session: ${e.message}", e)
            null
        }
    }

    private suspend fun loadRaceResults() {
        Log.d("RaceInfoFragment", "============ RACE RESULTS DEBUG ============")
        Log.d("RaceInfoFragment", "Fetching race results for session: $sessionKey")

        try {
            val results = RepositoryProvider.sessionResultRepository.getSessionResultsByKey(sessionKey)
            Log.d("RaceInfoFragment", "Race results API response size: ${results.size}")

            if (results.isNotEmpty()) {
                Log.d("RaceInfoFragment", "First 3 results: ${results.take(3)}")
            }

            val drivers = RepositoryProvider.driverRepository.getDriversBySession(sessionKey)
            Log.d("RaceInfoFragment", "Drivers for session size: ${drivers.size}")

            Log.d("RaceInfoFragment", "Results FINAL size: ${results.size}, drivers size: ${drivers.size}")

            if (results.isEmpty()) {
                binding.rvResults.visibility = View.GONE
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.text = "No race results available yet"
                Log.w("RaceInfoFragment", "Race results are EMPTY for sessionKey: $sessionKey")
            } else {
                binding.rvResults.visibility = View.VISIBLE
                binding.tvEmptyState.visibility = View.GONE
                val adapter = RaceResultsAdapter(results, drivers)
                binding.rvResults.layoutManager = LinearLayoutManager(requireContext())
                binding.rvResults.adapter = adapter
                Log.d("RaceInfoFragment", "Adapter set with ${results.size} items")
                Log.d("RaceInfoFragment", "RecyclerView adapter item count: ${adapter.itemCount}")
            }
        } catch (e: Exception) {
            Log.e("RaceInfoFragment", "ERROR fetching race results: ${e.message}", e)
            binding.rvResults.visibility = View.GONE
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.tvEmptyState.text = "Error loading race results"
        }
        Log.d("RaceInfoFragment", "============ END RACE RESULTS DEBUG ============")
    }
}