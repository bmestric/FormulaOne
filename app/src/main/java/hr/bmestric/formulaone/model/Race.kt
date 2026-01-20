package hr.bmestric.formulaone.model

data class Race(
    val id: Int,
    val name: String,
    val location: String,
    val country: String,
    val circuit: String,
    val dateStart: String,
    val dateEnd: String,
    val year: Int
)
