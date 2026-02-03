package hr.bmestric.formulaone.ui.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import hr.bmestric.formulaone.databinding.FragmentDriversBinding
import hr.bmestric.formulaone.framework.RepositoryProvider
import hr.bmestric.formulaone.framework.setupYearSpinner
import hr.bmestric.formulaone.ui.adapter.DriverAdapter
import kotlinx.coroutines.launch

class DriverFragment : Fragment() {

    private lateinit var binding: FragmentDriversBinding
    private var selectedYear: Int = 2025

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDriversBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupYearSpinner(binding.spinnerYear) { year ->
            selectedYear = year
            loadDrivers(year)
        }
    }


    fun loadDrivers(year: Int) {
        lifecycleScope.launch {
            try {
                binding.progressBar.visibility = View.VISIBLE
                binding.tvEmptyState.visibility = View.GONE

                val drivers = RepositoryProvider.driverRepository.getDriversByYear(year)

                if (drivers.isNotEmpty()) {
                    val adapter = DriverAdapter(drivers)

                    // Use GridLayoutManager in landscape, LinearLayoutManager in portrait
                    val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 2 else 1
                    binding.rvDrivers.layoutManager = if (spanCount > 1) {
                        GridLayoutManager(requireContext(), spanCount)
                    } else {
                        LinearLayoutManager(requireContext())
                    }
                    binding.rvDrivers.adapter = adapter
                    binding.rvDrivers.visibility = View.VISIBLE
                    binding.tvEmptyState.visibility = View.GONE
                } else {
                    binding.rvDrivers.visibility = View.GONE
                    binding.tvEmptyState.visibility = View.VISIBLE
                    binding.tvEmptyState.text = "No drivers found for $year"
                }

                binding.progressBar.visibility = View.GONE
            } catch (e: Exception) {
                binding.progressBar.visibility = View.GONE
                binding.rvDrivers.visibility = View.GONE
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.text = "Error loading drivers: ${e.message}"
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}