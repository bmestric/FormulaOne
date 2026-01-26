package hr.bmestric.formulaone.data.remote.mapper

import hr.bmestric.formulaone.data.remote.DTO.SessionResultDto
import hr.bmestric.formulaone.domain.model.SessionResult

fun SessionResultDto.toDomain() : SessionResult {
    return SessionResult(
        position = this.position,
        driverNumber = this.driverNumber,
        numberOfLaps = this.numberOfLaps,
        dnf = this.dnf,
        lapDuration = this.duration,
        gapToLeader = this.gapToLeader
    )
}

fun List<SessionResultDto>.toDomain() : List<SessionResult> {
    return this.map { it.toDomain() }
}