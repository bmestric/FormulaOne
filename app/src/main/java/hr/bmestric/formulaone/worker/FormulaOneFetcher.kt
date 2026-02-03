package hr.bmestric.formulaone.worker

import android.content.ContentValues
import android.content.Context
import hr.bmestric.formulaone.MEETINGS_CONTENT_URI
import hr.bmestric.formulaone.SESSIONS_CONTENT_URI
import hr.bmestric.formulaone.domain.model.Meeting
import hr.bmestric.formulaone.domain.model.Session
import hr.bmestric.formulaone.framework.RepositoryProvider
import hr.bmestric.formulaone.framework.sendBroadcast
import hr.bmestric.formulaone.receiver.FormulaOneReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FormulaOneFetcher(private val context: Context) {
    suspend fun fetchItems(year: Int = 2025) {

            val sessions = RepositoryProvider.sessionRepository.getSessionsByYear(year)
            sessions.forEach { session ->
                val values = ContentValues().apply {
                    put(Session::sessionKey.name, session.sessionKey)
                    put(Session::sessionType.name, session.sessionType)
                    put(Session::name.name, session.name)
                    put(Session::location.name, session.location)
                    put(Session::country.name, session.country)
                    put(Session::circuit.name, session.circuit)
                    put(Session::dateStart.name, session.dateStart)
                    put(Session::dateEnd.name, session.dateEnd)
                    put(Session::year.name, session.year)
                    put(Session::meetingKey.name, session.meetingKey)
                }

                context.contentResolver.insert(
                    SESSIONS_CONTENT_URI,
                    values
                )
            }

            val meetingKeys = sessions.map { it.meetingKey }.distinct()
            val meetings = meetingKeys.mapNotNull { key ->
                try {
                    RepositoryProvider.meetingRepository.getMeetingByKey(key)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }

            meetings.forEach { meeting ->
                val values = ContentValues().apply {
                    put(Meeting::meetingKey.name, meeting.meetingKey)
                    put(Meeting::circuitImage.name, meeting.circuitImage)
                    put(Meeting::circuitInfoUrl.name, meeting.circuitInfoUrl)
                }

                context.contentResolver.insert(
                    MEETINGS_CONTENT_URI,
                    values
                )
            }

            context.sendBroadcast<FormulaOneReceiver>()
        //}
    }
}

