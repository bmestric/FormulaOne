package hr.bmestric.formulaone.model

data class TrackLocation(
    val timestamp: String,
    val x: Int,
    val y: Int,
    val z: Int,
    val sessionKey: Int,
    val driverNumber: Int,
    val meetingKey: Int
)
