package hr.bmestric.formulaone.domain.model

data class Driver(
    val driverNumber: Int,
    val broadcastName: String,
    val fullName: String,
    val teamName: String,
    val teamColour: String,
    val headshotUrl: String?
)
