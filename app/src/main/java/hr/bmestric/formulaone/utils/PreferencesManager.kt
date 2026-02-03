package hr.bmestric.formulaone.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object PreferencesManager {
    private const val PREF_NAME = "formula_one_prefs"
    private const val KEY_LANGUAGE = "language"
    private const val KEY_THEME = "theme"

    const val LANGUAGE_ENGLISH = "en"
    const val LANGUAGE_CROATIAN = "hr"

    const val THEME_LIGHT = "light"
    const val THEME_DARK = "dark"

    private fun getPreferences(context: Context) : SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveLanguage(context: Context, language: String) {
        getPreferences(context)
            .edit {
                putString(KEY_LANGUAGE, language)
            }
    }

    fun getLanguage(context: Context): String {
        return getPreferences(context)
            .getString(KEY_LANGUAGE, LANGUAGE_ENGLISH) ?: LANGUAGE_ENGLISH
    }

    fun saveTheme(context: Context, theme: String) {
        getPreferences(context)
            .edit {
                putString(KEY_THEME, theme)
            }
    }

    fun getTheme(context: Context): String {
        return getPreferences(context)
            .getString(KEY_THEME, THEME_LIGHT) ?: THEME_LIGHT
    }

}