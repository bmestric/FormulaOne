package hr.bmestric.formulaone.data.impl

import hr.bmestric.formulaone.data.remote.api.MeetingApi
import hr.bmestric.formulaone.data.remote.mapper.toDomain
import hr.bmestric.formulaone.domain.model.Meeting
import hr.bmestric.formulaone.domain.repository.MeetingRepository

class MeetingRepositoryImpl(private val meetingApi: MeetingApi) : MeetingRepository {
    override suspend fun getMeetingsByYear(year: Int): List<Meeting> {
        val dtos = meetingApi.getMeetingsForYear(year)
        return dtos.toDomain()
    }
    override suspend fun getMeetingByKey(meetingKey: Int): Meeting? {
        val dtos = meetingApi.getMeetingByKey(meetingKey)
        return dtos.firstOrNull()?.toDomain()
    }
}
