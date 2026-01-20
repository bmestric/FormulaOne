package hr.bmestric.formulaone.api.DTO

import com.google.gson.annotations.SerializedName

data class SessionDto(
    @SerializedName("session_key") val sessionKey: Int,
    @SerializedName("session_type") val sessionType: String,
    @SerializedName("session_name") val sessionName: String,
    @SerializedName("date_start") val dateStart: String,
    @SerializedName("date_end") val dateEnd: String,
    @SerializedName("meeting_key") val meetingKey: Int,
    @SerializedName("circuit_key") val circuitKey: Int,
    @SerializedName("circuit_short_name") val circuitShortName: String,
    @SerializedName("country_key") val countryKey: Int,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("country_name") val countryName: String,
    @SerializedName("location") val location: String,
    @SerializedName("gmt_offset") val gmtOffset: String,
    @SerializedName("year") val year: Int
)
