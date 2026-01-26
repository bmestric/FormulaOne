package hr.bmestric.formulaone.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import hr.bmestric.formulaone.ui.activity.DATA_IMPORTED
import hr.bmestric.formulaone.ui.activity.HostActivity
import hr.bmestric.formulaone.framework.setBooleanPreference
import hr.bmestric.formulaone.framework.startActivity

class FormulaOneReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.setBooleanPreference(DATA_IMPORTED)
        context.startActivity<HostActivity>()
    }
}