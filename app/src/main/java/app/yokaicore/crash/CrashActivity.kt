package app.yokaicore.crash

import android.content.Intent
import android.os.Bundle
import androidx.core.view.WindowCompat
import app.yokaicore.presentation.crash.CrashScreen
import app.yokaicore.ui.base.activity.BaseActivity
import app.yokaicore.ui.main.MainActivity
import app.yokaicore.util.view.setComposeContent

class CrashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val exception = GlobalExceptionHandler.getThrowableFromIntent(intent)
        setComposeContent {
            CrashScreen(
                exception = exception,
                onRestartClick = {
                    finishAffinity()
                    startActivity(Intent(this@CrashActivity, MainActivity::class.java))
                },
            )
        }
    }
}
