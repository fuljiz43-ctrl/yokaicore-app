package app.yokaicore.presentation.category

import android.content.Context
import androidx.compose.runtime.Composable
import yokaicore.core.i18n.stringResource
import yokaicore.domain.category.model.Category
import yokaicore.i18n.MR
import yokaicore.presentation.core.i18n.stringResource

val Category.visualName: String
    @Composable
    get() = when {
        isSystemCategory -> stringResource(MR.strings.label_default)
        else -> name
    }

fun Category.visualName(context: Context): String =
    when {
        isSystemCategory -> context.stringResource(MR.strings.label_default)
        else -> name
    }
