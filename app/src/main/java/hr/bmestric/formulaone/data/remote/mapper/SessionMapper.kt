package hr.bmestric.formulaone.data.remote.mapper

import hr.bmestric.formulaone.data.remote.DTO.SessionDto
import hr.bmestric.formulaone.domain.model.Session

fun SessionDto.toDomain(): Session {
    return Session(
        sessionKey = this.sessionKey,
        sessionType = this.sessionType,
        name = this.sessionName,
        location = this.location,
        country = this.countryName,
        circuit = this.circuitShortName,
        dateStart = this.dateStart,
        dateEnd = this.dateEnd,
        year = this.year,
        meetingKey = this.meetingKey
    )
}

fun List<SessionDto>.toDomain(): List<Session> {
    return this.map { it.toDomain() }
}

