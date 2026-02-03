package hr.bmestric.formulaone.data.remote.mapper

import hr.bmestric.formulaone.data.remote.DTO.MeetingDto
import hr.bmestric.formulaone.domain.model.Meeting

fun MeetingDto.toDomain(): Meeting {
    return Meeting(
        meetingKey = this.meetingKey,
        circuitInfoUrl = this.circuitInfoUrl,
        circuitImage = this.circuitImage ?: ""
    )
}

fun List<MeetingDto>.toDomain(): List<Meeting> {
    return this.map { it.toDomain() }
}
