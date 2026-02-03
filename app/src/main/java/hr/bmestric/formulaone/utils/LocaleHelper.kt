package hr.bmestric.formulaone.utils

import android.content.Context
import java.util.Locale
import android.content.res.Configuration
import android.os.Build

object LocaleHelper {

    fun setLocale(context: Context, language: String) : Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
            context
        }
    }
}