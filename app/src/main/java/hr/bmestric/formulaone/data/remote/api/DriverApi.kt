package hr.bmestric.formulaone.data.remote.api

import hr.bmestric.formulaone.data.remote.DTO.DriverDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DriverApi {
    @GET("drivers")
    suspend fun getDriversBySession(
        @Query("session_key") sessionKey: Int): List<DriverDto>
}