package hr.bmestric.formulaone.domain.repository

import hr.bmestric.formulaone.domain.model.Driver

interface DriverRepository {
    suspend fun getDriversBySession(sessionKey: Int): List<Driver>
    suspend fun getDriverByNumber(sessionKey: Int, driverNumber: Int): Driver?
    suspend fun getDriversByYear(year: Int): List<Driver>
}