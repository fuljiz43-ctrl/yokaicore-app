package app.yokaicore.ui.setting.track

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import app.yokaicore.data.track.TrackerManager
import app.yokaicore.ui.base.activity.BaseActivity
import app.yokaicore.ui.main.MainActivity
import app.yokaicore.util.view.setComposeContent
import yokaicore.presentation.core.screens.LoadingScreen
import uy.kohesive.injekt.injectLazy

abstract class BaseOAuthLoginActivity : BaseActivity() {

    internal val trackerManager: TrackerManager by injectLazy()

    abstract fun handleResult(data: Uri?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setComposeContent {
            LoadingScreen()
        }

        handleResult(intent.data)
    }

    internal fun returnToSettings() {
        finish()

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }
}
