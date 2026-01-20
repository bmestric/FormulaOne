package hr.bmestric.formulaone.api.DTO

import com.google.gson.annotations.SerializedName

data class StartingGridDto(
    @SerializedName("position") val position: Int,
    @SerializedName("driver_number") val driverNumber: Int,
    @SerializedName("lap_duration") val lapDuration: Double?,
    @SerializedName("meeting_key") val meetingKey: Int,
    @SerializedName("session_key") val sessionKey: Int
)
