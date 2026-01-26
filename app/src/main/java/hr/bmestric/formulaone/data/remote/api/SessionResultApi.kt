package hr.bmestric.formulaone.data.remote.api

import hr.bmestric.formulaone.data.remote.DTO.SessionResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SessionResultApi {
    @GET("session_result")
    suspend fun getSessionResults(
        @Query("session_key") sessionKey: Int): List<SessionResultDto>

    @GET("session_result")
    suspend fun getTopPositions(
        @Query("session_key") sessionKey: Int,
        @Query("position<=") maxPosition: Int = 10
    ): List<SessionResultDto>
}