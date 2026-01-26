package hr.bmestric.formulaone.data.remote.mapper

import hr.bmestric.formulaone.data.remote.DTO.StartingGridDto
import hr.bmestric.formulaone.domain.model.StartingGrid

fun StartingGridDto.toDomain() : StartingGrid {
    return StartingGrid(
        position = this.position,
        driverNumber = this.driverNumber,
        lapDuration = this.lapDuration
    )
}
fun List<StartingGridDto>.toDomain(): List<StartingGrid> {
    return this.map { it.toDomain() }
}