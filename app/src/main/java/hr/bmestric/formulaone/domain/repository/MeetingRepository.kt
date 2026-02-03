package hr.bmestric.formulaone.domain.repository

import hr.bmestric.formulaone.domain.model.Meeting

interface MeetingRepository {
    suspend fun getMeetingsByYear(year: Int): List<Meeting>
    suspend fun getMeetingByKey(meetingKey: Int): Meeting?
}
