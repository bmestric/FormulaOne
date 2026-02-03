package hr.bmestric.formulaone.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.bmestric.formulaone.R
import hr.bmestric.formulaone.worker.FormulaOneWorker
import hr.bmestric.formulaone.databinding.ActivitySplashScreenBinding
import hr.bmestric.formulaone.framework.startActivity
import hr.bmestric.formulaone.framework.applyAnimation
import hr.bmestric.formulaone.framework.callDelayed
import hr.bmestric.formulaone.framework.getBooleanPreference
import hr.bmestric.formulaone.framework.isOnline
import hr.bmestric.formulaone.framework.setBooleanPreference

private const val DELAY = 3000L
const val DATA_IMPORTED = "hr.bmestric.formulaone.data_imported"

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startAnimations()
        redirect()
    }

    private fun startAnimations() {
        binding.tvSplash.applyAnimation(R.anim.blink)
        binding.ivSplash.applyAnimation(R.anim.rotate)
    }

    private fun redirect() {
        val dataImported = getBooleanPreference(DATA_IMPORTED)

        if (dataImported) {
            callDelayed(DELAY) {
                startActivity<HostActivity>()
            }
        } else {
            if (isOnline()) {
                val workRequest = OneTimeWorkRequest.from(FormulaOneWorker::class.java)
                WorkManager.getInstance(this).enqueueUniqueWork(
                    DATA_IMPORTED,
                    ExistingWorkPolicy.KEEP,
                    workRequest
                )


                val startTime = System.currentTimeMillis()

                WorkManager.getInstance(this).getWorkInfoByIdLiveData(workRequest.id)
                    .observe(this) { workInfo ->
                        if (workInfo != null && workInfo.state.isFinished) {
                                val elapsed = System.currentTimeMillis() - startTime
                                val remaining = (DELAY - elapsed).coerceAtLeast(0)
                                // Ensure splash is shown at least DELAY milliseconds
                                callDelayed(remaining) {
                                    setBooleanPreference(DATA_IMPORTED, true)
                                    startActivity<HostActivity>()
                                    finish()
                                }

                        }
                    }
            } else {
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) {
                    finish()
                }
            }
        }
    }
}