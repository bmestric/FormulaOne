package hr.bmestric.formulaone.api.DTO

import com.google.gson.annotations.SerializedName

data class IntervalDto(
    @SerializedName("date") val date: String,
    @SerializedName("session_key") val sessionKey: Int,
    @SerializedName("meeting_key") val meetingKey: Int,
    @SerializedName("driver_number") val driverNumber: Int,
    @SerializedName("gap_to_leader") val gapToLeader: Double?,
    @SerializedName("interval") val interval: Double?
)
