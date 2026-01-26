package hr.bmestric.formulaone.data.remote.api

import hr.bmestric.formulaone.data.remote.DTO.StartingGridDto
import retrofit2.http.GET
import retrofit2.http.Query

interface StartingGridApi {
    @GET("starting_grid")
    suspend fun getStartingGrid(@Query("session_key") sessionKey: Int): List<StartingGridDto>
}