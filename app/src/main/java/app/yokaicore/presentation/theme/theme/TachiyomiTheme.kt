package app.yokaicore.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import app.yokaicore.domain.ui.UiPreferences
import app.yokaicore.domain.ui.model.AppTheme
import app.yokaicore.presentation.theme.colorscheme.GreenAppleColorScheme
import app.yokaicore.presentation.theme.colorscheme.LavenderColorScheme
import app.yokaicore.presentation.theme.colorscheme.MidnightDuskColorScheme
import app.yokaicore.presentation.theme.colorscheme.MonetColorScheme
import app.yokaicore.presentation.theme.colorscheme.NordColorScheme
import app.yokaicore.presentation.theme.colorscheme.StrawberryColorScheme
import app.yokaicore.presentation.theme.colorscheme.YokaiCoreColorScheme
import app.yokaicore.presentation.theme.colorscheme.TakoColorScheme
import app.yokaicore.presentation.theme.colorscheme.TealTurqoiseColorScheme
import app.yokaicore.presentation.theme.colorscheme.TidalWaveColorScheme
import app.yokaicore.presentation.theme.colorscheme.YinYangColorScheme
import app.yokaicore.presentation.theme.colorscheme.YotsubaColorScheme
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

@Composable
fun YokaiCoreTheme(
    appTheme: AppTheme? = null,
    amoled: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val uiPreferences = Injekt.get<UiPreferences>()
    BaseYokaiCoreTheme(
        appTheme = appTheme ?: uiPreferences.appTheme().get(),
        isAmoled = amoled ?: uiPreferences.themeDarkAmoled().get(),
        content = content,
    )
}

@Composable
fun YokaiCorePreviewTheme(
    appTheme: AppTheme = AppTheme.DEFAULT,
    isAmoled: Boolean = false,
    content: @Composable () -> Unit,
) = BaseYokaiCoreTheme(appTheme, isAmoled, content)

@Composable
private fun BaseYokaiCoreTheme(
    appTheme: AppTheme,
    isAmoled: Boolean,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = getThemeColorScheme(appTheme, isAmoled),
        content = content,
    )
}

@Composable
@ReadOnlyComposable
private fun getThemeColorScheme(
    appTheme: AppTheme,
    isAmoled: Boolean,
): ColorScheme {
    val colorScheme = when (appTheme) {
        AppTheme.DEFAULT -> YokaiCoreColorScheme
        AppTheme.MONET -> MonetColorScheme(LocalContext.current)
        AppTheme.GREEN_APPLE -> GreenAppleColorScheme
        AppTheme.LAVENDER -> LavenderColorScheme
        AppTheme.MIDNIGHT_DUSK -> MidnightDuskColorScheme
        AppTheme.NORD -> NordColorScheme
        AppTheme.STRAWBERRY_DAIQUIRI -> StrawberryColorScheme
        AppTheme.TAKO -> TakoColorScheme
        AppTheme.TEALTURQUOISE -> TealTurqoiseColorScheme
        AppTheme.TIDAL_WAVE -> TidalWaveColorScheme
        AppTheme.YINYANG -> YinYangColorScheme
        AppTheme.YOTSUBA -> YotsubaColorScheme
        else -> YokaiCoreColorScheme
    }
    return colorScheme.getColorScheme(
        isSystemInDarkTheme(),
        isAmoled,
    )
}
