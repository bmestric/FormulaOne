package hr.bmestric.formulaone.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import hr.bmestric.formulaone.R
import hr.bmestric.formulaone.databinding.FragmentSettingsBinding
import hr.bmestric.formulaone.ui.activity.HostActivity
import hr.bmestric.formulaone.utils.LocaleHelper
import hr.bmestric.formulaone.utils.PreferencesManager
import hr.bmestric.formulaone.utils.ThemeHelper

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private var selectedLanguage: String = PreferencesManager.LANGUAGE_ENGLISH
    private var selectedTheme: String = PreferencesManager.THEME_LIGHT
    private var hasChanges = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCurrentSettings()
        setupLanguageSelection()
        setupThemeSelection()
        setupApplyButton()
    }

    private fun setupApplyButton() {
        binding.btnApplySettings.setOnClickListener {
            applySettings()
        }
        updateApplyButtonState()
    }

    private fun updateApplyButtonState() {
        binding.btnApplySettings.isEnabled = hasChanges
    }

    private fun applySettings() {
        val currentLanguage = PreferencesManager.getLanguage(requireContext())
        val currentTheme = PreferencesManager.getTheme(requireContext())

        val languageChanged = selectedLanguage != currentLanguage
        val themeChanged = selectedTheme != currentTheme

        if (languageChanged) {
            PreferencesManager.saveLanguage(requireContext(), selectedLanguage)
        }

        if (themeChanged) {
            PreferencesManager.saveTheme(requireContext(), selectedTheme)
        }

        if (languageChanged) {
            LocaleHelper.setLocale(requireContext(), selectedLanguage)
            restartActivity()
        } else if (themeChanged) {
            ThemeHelper.applyTheme(selectedTheme)
            hasChanges = false
            updateApplyButtonState()

            Snackbar.make(
                binding.root,
                getString(R.string.settings_applied),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun loadCurrentSettings() {
        selectedLanguage = PreferencesManager.getLanguage(requireContext())
        when (selectedLanguage) {
            PreferencesManager.LANGUAGE_ENGLISH -> binding.rbEnglish.isChecked = true
            PreferencesManager.LANGUAGE_CROATIAN -> binding.rbCroatian.isChecked = true
        }

        selectedTheme = PreferencesManager.getTheme(requireContext())
        when (selectedTheme) {
            PreferencesManager.THEME_LIGHT -> binding.rbLightMode.isChecked = true
            PreferencesManager.THEME_DARK -> binding.rbDarkMode.isChecked = true
        }
    }

    private fun setupLanguageSelection() {
        binding.rgLanguage.setOnCheckedChangeListener { _, checkedId ->
            val newLanguage = when (checkedId) {
                R.id.rbEnglish -> PreferencesManager.LANGUAGE_ENGLISH
                R.id.rbCroatian -> PreferencesManager.LANGUAGE_CROATIAN
                else -> PreferencesManager.LANGUAGE_ENGLISH
            }

            if (newLanguage != PreferencesManager.getLanguage(requireContext())) {
                selectedLanguage = newLanguage
                hasChanges = true
                updateApplyButtonState()
            }
        }
    }


    private fun setupThemeSelection() {
        binding.rgTheme.setOnCheckedChangeListener { _, checkedId ->
            val newTheme = when (checkedId) {
                R.id.rbLightMode -> PreferencesManager.THEME_LIGHT
                R.id.rbDarkMode -> PreferencesManager.THEME_DARK
                else -> PreferencesManager.THEME_LIGHT
            }

            if (newTheme != PreferencesManager.getTheme(requireContext())) {
                selectedTheme = newTheme
                hasChanges = true
                updateApplyButtonState()
            }
        }
    }

    private fun restartActivity() {
        val intent = Intent(requireContext(), HostActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}