package hr.bmestric.formulaone.data.remote.mapper

import hr.bmestric.formulaone.data.remote.DTO.DriverDto
import hr.bmestric.formulaone.domain.model.Driver


fun DriverDto.toDomain() : Driver {
    return Driver(
        driverNumber = this.driverNumber,
        broadcastName = this.broadcastName,
        fullName = this.fullName,
        teamName = this.teamName,
        teamColour = this.teamColour,
        headshotUrl = this.headshotUrl ?: ""
    )
}

fun List<DriverDto>.toDomain(): List<Driver> {
    return this.map { it.toDomain() }
}