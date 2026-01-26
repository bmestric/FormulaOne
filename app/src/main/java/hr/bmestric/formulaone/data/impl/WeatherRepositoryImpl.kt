package hr.bmestric.formulaone.data.impl

import hr.bmestric.formulaone.data.remote.api.WeatherApi
import hr.bmestric.formulaone.data.remote.mapper.toDomain
import hr.bmestric.formulaone.domain.model.Weather
import hr.bmestric.formulaone.domain.repository.WeatherRepository

class WeatherRepositoryImpl(private val weatherApi: WeatherApi) : WeatherRepository {
    override suspend fun getWeather(sessionKey: Int): Weather? {
        val dtos = weatherApi.getWeatherBySession(sessionKey)
        return dtos.firstOrNull()?.toDomain()
    }
}