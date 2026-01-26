package hr.bmestric.formulaone.data.impl

import hr.bmestric.formulaone.data.remote.api.StartingGridApi
import hr.bmestric.formulaone.data.remote.mapper.toDomain
import hr.bmestric.formulaone.domain.model.StartingGrid
import hr.bmestric.formulaone.domain.repository.StartingGridRepository

class StartingGridRepositoryImpl(private val startingGridApi: StartingGridApi) : StartingGridRepository {
    override suspend fun getStartingGrid(sessionKey: Int): List<StartingGrid> {
        val dtos = startingGridApi.getStartingGrid(sessionKey)
        return dtos.toDomain()
    }
}