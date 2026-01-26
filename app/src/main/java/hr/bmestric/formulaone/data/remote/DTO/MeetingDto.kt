package hr.bmestric.formulaone.data.remote.DTO

import com.google.gson.annotations.SerializedName

data class MeetingDto(
    @SerializedName("meeting_key") val meetingKey: Int,
    @SerializedName("meeting_name") val meetingName: String,
    @SerializedName("location") val location: String,
    @SerializedName("country_name") val countryName: String,
    @SerializedName("circuit_short_name") val circuitShortName: String,
    @SerializedName("date_start") val dateStart: String,
    @SerializedName("date_end") val dateEnd: String,
    @SerializedName("year") val year: Int
)
