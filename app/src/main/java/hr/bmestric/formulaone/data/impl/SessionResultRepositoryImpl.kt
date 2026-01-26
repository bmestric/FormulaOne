package hr.bmestric.formulaone.data.impl

import hr.bmestric.formulaone.data.remote.api.SessionResultApi
import hr.bmestric.formulaone.data.remote.mapper.toDomain
import hr.bmestric.formulaone.domain.model.SessionResult
import hr.bmestric.formulaone.domain.repository.SessionResultRepository

class SessionResultRepositoryImpl(private val sessionResultApi: SessionResultApi) : SessionResultRepository {
    override suspend fun getSessionResultsByKey(sessionKey: Int): List<SessionResult> {
        val dtos = sessionResultApi.getSessionResults(sessionKey)
        return dtos.toDomain()
    }

    override suspend fun getDriverResults(
        sessionKey: Int,
        driverNumber: Int
    ): SessionResult? {
        val allResults = getSessionResultsByKey(sessionKey)
        return allResults.find { it.driverNumber == driverNumber }
    }

    override suspend fun getTopTenSessionResults(sessionKey: Int): List<SessionResult> {
        val dtos = sessionResultApi.getTopPositions(sessionKey)
        return dtos.toDomain()
    }
}