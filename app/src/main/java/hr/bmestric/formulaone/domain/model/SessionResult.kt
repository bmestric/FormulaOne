package hr.bmestric.formulaone.domain.model

data class SessionResult(
    val position: Int,
    val driverNumber: Int,
    val numberOfLaps: Int,
    val dnf: Boolean,
    val lapDuration: Double?,
    val gapToLeader: String?
)
