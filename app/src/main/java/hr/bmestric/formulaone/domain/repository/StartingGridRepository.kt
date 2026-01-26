package hr.bmestric.formulaone.domain.repository

import hr.bmestric.formulaone.domain.model.StartingGrid

interface StartingGridRepository {
    suspend fun getStartingGrid(sessionKey: Int): List<StartingGrid>
}