package hr.bmestric.formulaone.api.DTO

import com.google.gson.annotations.SerializedName

data class LapDto(
    @SerializedName("meeting_key") val meetingKey: Int,
    @SerializedName("session_key") val sessionKey: Int,
    @SerializedName("driver_number") val driverNumber: Int,
    @SerializedName("lap_number") val lapNumber: Int,
    @SerializedName("date_start") val dateStart: String,
    @SerializedName("duration_sector_1") val durationSector1: Double?,
    @SerializedName("duration_sector_2") val durationSector2: Double?,
    @SerializedName("duration_sector_3") val durationSector3: Double?,
    @SerializedName("i1_speed") val i1Speed: Double?,
    @SerializedName("i2_speed") val i2Speed: Double?,
    @SerializedName("is_pit_out_lap") val isPitOutLap: Boolean,
    @SerializedName("lap_duration") val lapDuration: Double?,
    @SerializedName("segments_sector_1") val segmentsSector1: List<Int>,
    @SerializedName("segments_sector_2") val segmentsSector2: List<Int>,
    @SerializedName("segments_sector_3") val segmentsSector3: List<Int>,
    @SerializedName("st_speed") val stSpeed: Double?
)
