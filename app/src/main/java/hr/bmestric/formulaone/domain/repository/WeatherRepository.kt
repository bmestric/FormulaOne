package hr.bmestric.formulaone.domain.repository

import hr.bmestric.formulaone.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeather(sessionKey: Int): Weather?
}