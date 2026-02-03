package hr.bmestric.formulaone.data.remote.api

import hr.bmestric.formulaone.data.remote.DTO.MeetingDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MeetingApi {
    @GET("meetings")
    suspend fun getMeetingsForYear(
        @Query("year") year: Int
    ): List<MeetingDto>

    @GET("meetings")
    suspend fun getMeetingByKey(
        @Query("meeting_key") meetingKey: Int
    ): List<MeetingDto>
}