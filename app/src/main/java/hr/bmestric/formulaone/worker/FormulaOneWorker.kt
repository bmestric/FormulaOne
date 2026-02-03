package hr.bmestric.formulaone.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class FormulaOneWorker(private val context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        FormulaOneFetcher(context).fetchItems()
        return Result.success()
    }

}