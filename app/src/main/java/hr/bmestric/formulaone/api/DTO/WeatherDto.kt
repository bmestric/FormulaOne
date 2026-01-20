package hr.bmestric.formulaone.api.DTO

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("date") val date: String,
    @SerializedName("session_key") val sessionKey: Int,
    @SerializedName("meeting_key") val meetingKey: Int,
    @SerializedName("rainfall") val rainfall: Int,
    @SerializedName("track_temperature") val trackTemperature: Double,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("pressure") val pressure: Double,
    @SerializedName("air_temperature") val airTemperature: Double,
    @SerializedName("wind_direction") val windDirection: Int
)
