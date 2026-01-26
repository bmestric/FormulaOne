package hr.bmestric.formulaone.data.remote.api

import hr.bmestric.formulaone.data.remote.DTO.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherBySession(@Query("session_key") sessionKey: Int): List<WeatherDto>
}