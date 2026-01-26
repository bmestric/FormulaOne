package hr.bmestric.formulaone.data.remote.mapper

import hr.bmestric.formulaone.data.remote.DTO.WeatherDto
import hr.bmestric.formulaone.domain.model.Weather

fun WeatherDto.toDomain(): Weather {
    return Weather(
        rainfall = this.rainfall,
        airTemperature = this.airTemperature,
    )
}

fun List<WeatherDto>.toDomain(): List<Weather> {
    return this.map { it.toDomain() }
}