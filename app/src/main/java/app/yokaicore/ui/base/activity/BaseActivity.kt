package app.yokaicore.ui.base.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.yokaicore.ui.base.delegate.SecureActivityDelegate
import app.yokaicore.ui.base.delegate.SecureActivityDelegateImpl
import app.yokaicore.ui.base.delegate.ThemingDelegate
import app.yokaicore.ui.base.delegate.ThemingDelegateImpl
import app.yokaicore.util.system.prepareTabletUiContext

open class BaseActivity :
    AppCompatActivity(),
    SecureActivityDelegate by SecureActivityDelegateImpl(),
    ThemingDelegate by ThemingDelegateImpl() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(newBase.prepareTabletUiContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        applyAppTheme(this)
        super.onCreate(savedInstanceState)
    }
}
