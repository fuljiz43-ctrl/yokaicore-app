package app.yokaicore.ui.base.delegate

import android.app.Activity
import app.yokaicore.domain.ui.UiPreferences
import app.yokaicore.domain.ui.model.AppTheme
import app.yokaicore.R
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

interface ThemingDelegate {
    fun applyAppTheme(activity: Activity)

    companion object {
        fun getThemeResIds(appTheme: AppTheme, isAmoled: Boolean): List<Int> {
            val resIds = mutableListOf<Int>()
            when (appTheme) {
                AppTheme.MONET -> {
                    resIds += R.style.Theme_YokaiCore_Monet
                }
                AppTheme.GREEN_APPLE -> {
                    resIds += R.style.Theme_YokaiCore_GreenApple
                }
                AppTheme.LAVENDER -> {
                    resIds += R.style.Theme_YokaiCore_Lavender
                }
                AppTheme.MIDNIGHT_DUSK -> {
                    resIds += R.style.Theme_YokaiCore_MidnightDusk
                }
                AppTheme.NORD -> {
                    resIds += R.style.Theme_YokaiCore_Nord
                }
                AppTheme.STRAWBERRY_DAIQUIRI -> {
                    resIds += R.style.Theme_YokaiCore_StrawberryDaiquiri
                }
                AppTheme.TAKO -> {
                    resIds += R.style.Theme_YokaiCore_Tako
                }
                AppTheme.TEALTURQUOISE -> {
                    resIds += R.style.Theme_YokaiCore_TealTurquoise
                }
                AppTheme.YINYANG -> {
                    resIds += R.style.Theme_YokaiCore_YinYang
                }
                AppTheme.YOTSUBA -> {
                    resIds += R.style.Theme_YokaiCore_Yotsuba
                }
                AppTheme.TIDAL_WAVE -> {
                    resIds += R.style.Theme_YokaiCore_TidalWave
                }
                else -> {
                    resIds += R.style.Theme_YokaiCore
                }
            }

            if (isAmoled) {
                resIds += R.style.ThemeOverlay_YokaiCore_Amoled
            }

            return resIds
        }
    }
}

class ThemingDelegateImpl : ThemingDelegate {
    override fun applyAppTheme(activity: Activity) {
        val uiPreferences = Injekt.get<UiPreferences>()
        ThemingDelegate.getThemeResIds(uiPreferences.appTheme().get(), uiPreferences.themeDarkAmoled().get())
            .forEach(activity::setTheme)
    }
}
