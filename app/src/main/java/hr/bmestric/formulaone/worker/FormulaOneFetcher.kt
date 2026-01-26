package hr.bmestric.formulaone.worker

import android.content.Context
import hr.bmestric.formulaone.data.remote.NetworkModule
import hr.bmestric.formulaone.data.remote.api.SessionApi
import hr.bmestric.formulaone.domain.model.Session
import hr.bmestric.formulaone.receiver.FormulaOneReceiver
import hr.bmestric.formulaone.framework.sendBroadcast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class FormulaOneFetcher(private val context: Context) {

    private val sessionApi: SessionApi = NetworkModule.createService(SessionApi::class.java)

    fun fetchItems(year: Int = 2025) {
        CoroutineScope(Dispatchers.IO).launch {
            sessionApi.getRaceSession(year)
            //Save to database
        }


        Thread.sleep(6000)

        context.sendBroadcast<FormulaOneReceiver>()
    }
}