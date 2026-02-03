package hr.bmestric.formulaone.domain.model

data class SessionResult(
    val position: Int,
    val driverNumber: Int,
    val numberOfLaps: Int,
    val dnf: Boolean,
    val lapDuration: Double?,
    val gapToLeader: String?  // Changed from Double - can be "+1 LAP", "+2 LAPS", or time gap
)
