package hr.bmestric.formulaone.framework

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.edit
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import hr.bmestric.formulaone.MEETINGS_CONTENT_URI
import hr.bmestric.formulaone.SESSIONS_CONTENT_URI
import hr.bmestric.formulaone.data.local.mapper.toMeetings
import hr.bmestric.formulaone.data.local.mapper.toSessions
import hr.bmestric.formulaone.domain.model.Meeting
import hr.bmestric.formulaone.domain.model.Session
import java.text.SimpleDateFormat
import java.util.Locale

fun View.applyAnimation(animationId: Int)
 = startAnimation(AnimationUtils.loadAnimation(context, animationId))

inline fun <reified T : Activity> Context.startActivity() {
    startActivity(Intent(this, T::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    })
}

inline fun <reified T : BroadcastReceiver> Context.sendBroadcast() {
    sendBroadcast(Intent(this, T::class.java))
}

fun callDelayed(delay: Long, work: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(
        work,
        delay
    )
}

fun Context.getBooleanPreference(key: String) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .getBoolean(key, false)

fun Context.setBooleanPreference(key: String, value: Boolean = true) =
    PreferenceManager.getDefaultSharedPreferences(this)
        .edit {
            putBoolean(key, value)
        }
fun Context.isOnline() : Boolean {
    val connectivityManager = getSystemService<ConnectivityManager>()
    connectivityManager?.activeNetwork?.let { network ->
        connectivityManager.getNetworkCapabilities(network)?.let { capabilities ->
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        }
    }
    return false
}

fun Context.fetchSessions() : List<Session> {
    return contentResolver.query(
        SESSIONS_CONTENT_URI,
        null, null, null, null
    )?.toSessions() ?: emptyList()
}

fun Context.fetchMeetings() : List<Meeting> {
    return contentResolver.query(
        MEETINGS_CONTENT_URI,
        null, null, null, null
    )?.toMeetings() ?: emptyList()
}

fun String.toFormattedRaceDate(): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        val date = inputFormat.parse(this)

        val outputFormat = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
        date?.let { outputFormat.format(it) } ?: this
    } catch (e: Exception) {
        this
    }
}


fun Fragment.setupYearSpinner(
    spinner: Spinner,
    defaultYear: Int = 2025,
    onYearSelected: (Int) -> Unit
) {
    val years = listOf(2023, 2024, 2025, 2026)

    val adapter = ArrayAdapter(
        requireContext(),
        android.R.layout.simple_spinner_item,
        years
    )
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

    spinner.adapter = adapter
    spinner.setSelection(years.indexOf(defaultYear)) // Default to specified year

    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onYearSelected(years[position])
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Do nothing
        }
    }
}
