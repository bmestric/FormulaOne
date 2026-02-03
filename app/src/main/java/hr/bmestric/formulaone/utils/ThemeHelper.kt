package hr.bmestric.formulaone.utils

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {

    fun applyTheme(theme: String) {
        when (theme) {
            PreferencesManager.THEME_LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            PreferencesManager.THEME_DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    fun applyStoredTheme(context: Context) {
        val theme = PreferencesManager.getTheme(context)
        applyTheme(theme)
    }
}