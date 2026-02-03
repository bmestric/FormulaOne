package hr.bmestric.formulaone.domain.repository

import hr.bmestric.formulaone.domain.model.Session

interface SessionRepository {
    suspend fun getSessionsByKey(sessionKey: Int) : Session?
    suspend fun getSessionsByYear(year: Int) : List<Session>
    suspend fun getSessionsByMeeting(meetingKey: Int) : List<Session>
}