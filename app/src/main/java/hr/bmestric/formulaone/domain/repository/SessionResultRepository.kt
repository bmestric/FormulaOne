package hr.bmestric.formulaone.domain.repository

import hr.bmestric.formulaone.domain.model.SessionResult

interface SessionResultRepository {
    suspend fun getSessionResultsByKey(sessionKey: Int): List<SessionResult>
    suspend fun getDriverResults(sessionKey: Int, driverNumber: Int): SessionResult?
    suspend fun getTopTenSessionResults(sessionKey: Int): List<SessionResult>
}