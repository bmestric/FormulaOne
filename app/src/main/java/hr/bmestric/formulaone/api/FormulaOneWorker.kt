package hr.bmestric.formulaone.api

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class FormulaOneWorker(private val context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        FormulaOneFetcher(context).fetchItems()
        return Result.success()
    }

}