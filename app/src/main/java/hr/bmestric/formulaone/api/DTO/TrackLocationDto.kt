package hr.bmestric.formulaone.api.DTO

import com.google.gson.annotations.SerializedName

data class TrackLocationDto (
    @SerializedName("date") val date: String,
    @SerializedName("session_key") val sessionKey: Int,
    @SerializedName("meeting_key") val meetingKey: Int,
    @SerializedName("driver_number") val driverNumber: Int,
    @SerializedName("x") val x: Int,
    @SerializedName("y") val y: Int,
    @SerializedName("z") val z: Int
)
