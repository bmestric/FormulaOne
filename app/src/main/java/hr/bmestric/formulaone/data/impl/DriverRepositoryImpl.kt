package hr.bmestric.formulaone.data.impl

import hr.bmestric.formulaone.data.remote.api.DriverApi
import hr.bmestric.formulaone.data.remote.api.SessionApi
import hr.bmestric.formulaone.data.remote.mapper.toDomain
import hr.bmestric.formulaone.domain.model.Driver
import hr.bmestric.formulaone.domain.repository.DriverRepository

class DriverRepositoryImpl(
    private val driverApi: DriverApi,
    private val sessionApi: SessionApi
) : DriverRepository {
    override suspend fun getDriversBySession(sessionKey: Int): List<Driver> {
        val dtos = driverApi.getDriversBySession(sessionKey = sessionKey)
        return dtos.toDomain()
    }

    override suspend fun getDriverByNumber(
        sessionKey: Int,
        driverNumber: Int
    ): Driver? {
        val allDrivers = getDriversBySession(sessionKey)
        return allDrivers.find { it.driverNumber == driverNumber }
    }

    override suspend fun getDriversByYear(year: Int): List<Driver> {
        val sessions = sessionApi.getRaceSession(year = year, sessionType = "Race")

        if (sessions.isEmpty()) return emptyList()

        val latestSession = sessions.maxByOrNull { it.dateStart }

        return if (latestSession != null) {
            getDriversBySession(latestSession.sessionKey)
        } else {
            emptyList()
        }
    }
}