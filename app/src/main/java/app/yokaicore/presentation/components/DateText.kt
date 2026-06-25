package app.yokaicore.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import app.yokaicore.domain.ui.UiPreferences
import app.yokaicore.util.lang.toRelativeString
import yokaicore.i18n.MR
import yokaicore.presentation.core.i18n.stringResource
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.Date

@Composable
fun relativeDateText(
    dateEpochMillis: Long,
): String {
    return relativeDateText(
        date = Date(dateEpochMillis).takeIf { dateEpochMillis > 0L },
    )
}

@Composable
fun relativeDateText(
    date: Date?,
): String {
    val context = LocalContext.current

    val preferences = remember { Injekt.get<UiPreferences>() }
    val relativeTime = remember { preferences.relativeTime().get() }
    val dateFormat = remember { UiPreferences.dateFormat(preferences.dateFormat().get()) }

    return date
        ?.toRelativeString(
            context = context,
            relative = relativeTime,
            dateFormat = dateFormat,
        )
        ?: stringResource(MR.strings.not_applicable)
}
