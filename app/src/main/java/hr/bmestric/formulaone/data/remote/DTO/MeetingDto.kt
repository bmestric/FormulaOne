package hr.bmestric.formulaone.data.remote.DTO

import com.google.gson.annotations.SerializedName

data class MeetingDto(
    @SerializedName("circuit_key") val circuitKey: Int,
    @SerializedName("circuit_info_url") val circuitInfoUrl: String,
    @SerializedName("circuit_image") val circuitImage: String,
    @SerializedName("circuit_short_name") val circuitShortName: String,
    @SerializedName("circuit_type") val circuitType: String,
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("country_flag") val countryFlag: String,
    @SerializedName("country_key") val countryKey: Int,
    @SerializedName("country_name") val countryName: String,
    @SerializedName("date_end") val dateEnd: String,
    @SerializedName("date_start") val dateStart: String,
    @SerializedName("gmt_offset") val gmtOffset: String,
    @SerializedName("location") val location: String,
    @SerializedName("meeting_key") val meetingKey: Int,
    @SerializedName("meeting_name") val meetingName: String,
    @SerializedName("meeting_official_name") val meetingOfficialName: String,
    @SerializedName("year") val year: Int
)
