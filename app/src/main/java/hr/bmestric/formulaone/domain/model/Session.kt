package hr.bmestric.formulaone.domain.model

data class Session(
    val sessionKey: Int,
    val sessionType: String,
    val name: String,
    val location: String,
    val country: String,
    val circuit: String,
    val dateStart: String,
    val dateEnd: String,
    val year: Int,
    val meetingKey: Int
)
