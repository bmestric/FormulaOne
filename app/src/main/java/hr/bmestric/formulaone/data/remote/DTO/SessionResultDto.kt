package hr.bmestric.formulaone.data.remote.DTO

import com.google.gson.annotations.SerializedName

data class SessionResultDto(
    @SerializedName("session_key") val sessionKey: Int,
    @SerializedName("meeting_key") val meetingKey: Int,
    @SerializedName("position") val position: Int,
    @SerializedName("driver_number") val driverNumber: Int,
    @SerializedName("number_of_laps") val numberOfLaps: Int,
    @SerializedName("duration") val duration: Double?,
    @SerializedName("gap_to_leader") val gapToLeader: String?,
    @SerializedName("dnf") val dnf: Boolean,
    @SerializedName("dns") val dns: Boolean,
    @SerializedName("dsq") val dsq: Boolean
)
