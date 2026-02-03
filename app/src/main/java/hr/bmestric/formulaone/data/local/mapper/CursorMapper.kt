package hr.bmestric.formulaone.data.local.mapper

import android.database.Cursor
import hr.bmestric.formulaone.domain.model.Meeting
import hr.bmestric.formulaone.domain.model.Session


fun Cursor.toSession(): Session {
    return Session(
        sessionKey = getInt(getColumnIndexOrThrow(Session::sessionKey.name)),
        sessionType = getString(getColumnIndexOrThrow(Session::sessionType.name)),
        name = getString(getColumnIndexOrThrow(Session::name.name)),
        location = getString(getColumnIndexOrThrow(Session::location.name)),
        country = getString(getColumnIndexOrThrow(Session::country.name)),
        circuit = getString(getColumnIndexOrThrow(Session::circuit.name)),
        dateStart = getString(getColumnIndexOrThrow(Session::dateStart.name)),
        dateEnd = getString(getColumnIndexOrThrow(Session::dateEnd.name)),
        year = getInt(getColumnIndexOrThrow(Session::year.name)),
        meetingKey = getInt(getColumnIndexOrThrow(Session::meetingKey.name))
    )
}

fun Cursor.toMeeting(): Meeting {
    return Meeting(
        meetingKey = getInt(getColumnIndexOrThrow(Meeting::meetingKey.name)),
        circuitInfoUrl = getString(getColumnIndexOrThrow(Meeting::circuitInfoUrl.name)),
        circuitImage = getString(getColumnIndexOrThrow(Meeting::circuitImage.name))
    )
}

fun Cursor.toSessions(): List<Session> {
    val sessions = mutableListOf<Session>()
    use {
        while (moveToNext()) {
            sessions.add(toSession())
        }
    }
    return sessions
}

fun Cursor.toMeetings(): List<Meeting> {
    val meetings = mutableListOf<Meeting>()
    use {
        while (moveToNext()) {
            meetings.add(toMeeting())
        }
    }
    return meetings
}

fun Cursor.toMeetingMap(): Map<Int, Meeting> {
    val meetingsMap = mutableMapOf<Int, Meeting>()
    use {
        while (moveToNext()) {
            val meeting = toMeeting()
            meetingsMap[meeting.meetingKey] = meeting
        }
    }
    return meetingsMap
}

