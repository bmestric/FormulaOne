package hr.bmestric.formulaone.model

data class Lap(
    val lapNumber: Int,
    val durationSector1: Double?,
    val durationSector2: Double?,
    val durationSector3: Double?,
    val lapDuration: Double?,
    val i1Speed: Double?,
    val i2Speed: Double?,
    val isPitOutLap: Boolean
)
