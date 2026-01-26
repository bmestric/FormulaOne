package hr.bmestric.formulaone.data.impl

import hr.bmestric.formulaone.data.remote.api.SessionApi
import hr.bmestric.formulaone.data.remote.mapper.toDomain
import hr.bmestric.formulaone.domain.model.Session
import hr.bmestric.formulaone.domain.repository.SessionRepository

class SessionRepositoryImpl(private val sessionApi: SessionApi) : SessionRepository {
    override suspend fun getSessionsByKey(sessionKey: Int): Session? {
        val dtos = sessionApi.getSessionsByKey(sessionKey)
        return dtos.firstOrNull()?.toDomain()
    }

    override suspend fun getSessionsByYear(year: Int): List<Session> {
        val dtos = sessionApi.getRaceSession(year = year)
        return dtos.toDomain()
    }

}