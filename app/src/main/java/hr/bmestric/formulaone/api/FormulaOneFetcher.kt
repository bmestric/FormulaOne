package hr.bmestric.formulaone.api

import android.content.Context
import hr.bmestric.formulaone.FormulaOneReceiver
import hr.bmestric.formulaone.framework.sendBroadcast

class FormulaOneFetcher(private val context: Context) {

    fun fetchItems(count: Int = 10) {
        Thread.sleep(6000)

        context.sendBroadcast<FormulaOneReceiver>()
    }
}