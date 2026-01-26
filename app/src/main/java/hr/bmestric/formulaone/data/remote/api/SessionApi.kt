package hr.bmestric.formulaone.data.remote.api

import hr.bmestric.formulaone.data.remote.DTO.SessionDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SessionApi {
    @GET("sessions")
    suspend fun getSessionsByMeeting(
        @Query("meeting_key") meetingKey: Int): List<SessionDto>

    @GET("sessions")
    suspend fun getSessionsByKey(
        @Query("session_key") meetingKey: Int): List<SessionDto>

    @GET("sessions")
    suspend fun getRaceSession(
        @Query("year") year: Int,
        @Query("session_type") sessionType: String = "Race",): List<SessionDto>
}