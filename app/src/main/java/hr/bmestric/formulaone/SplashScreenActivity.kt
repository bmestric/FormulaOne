package hr.bmestric.formulaone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import hr.bmestric.formulaone.api.FormulaOneWorker
import hr.bmestric.formulaone.databinding.ActivitySplashScreenBinding
import hr.bmestric.formulaone.framework.startActivity
import hr.bmestric.formulaone.framework.applyAnimation
import hr.bmestric.formulaone.framework.callDelayed
import hr.bmestric.formulaone.framework.getBooleanPreference
import hr.bmestric.formulaone.framework.isOnline

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
        if(getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) { startActivity<HostActivity>() }
        } else {
            if(isOnline()) {
                WorkManager.getInstance(this).apply {
                        enqueueUniqueWork(
                            DATA_IMPORTED,
                            ExistingWorkPolicy.KEEP,
                            OneTimeWorkRequest.from(
                                FormulaOneWorker::class.java
                            )
                        )
                    }
            } else {
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) { finish() }
            }
        }
    }
}