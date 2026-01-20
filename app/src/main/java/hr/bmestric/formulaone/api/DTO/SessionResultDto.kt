package hr.bmestric.formulaone.api.DTO

import com.google.gson.annotations.SerializedName

data class SessionResultDto(
    @SerializedName("position") val position: Int,
    @SerializedName("driver_number") val driverNumber: Int,
    @SerializedName("number_of_laps") val numberOfLaps: Int,
    @SerializedName("dnf") val dnf: Boolean,
    @SerializedName("lap_duration") val lapDuration: Double?,
    @SerializedName("gap_to_leader") val gapToLeader: Double?
)
