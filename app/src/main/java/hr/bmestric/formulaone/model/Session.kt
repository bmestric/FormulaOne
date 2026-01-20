package hr.bmestric.formulaone.model

data class Session(
    val id: Int,
    val type: String,
    val name: String,
    val dateStart: String,
    val dateEnd: String
)
