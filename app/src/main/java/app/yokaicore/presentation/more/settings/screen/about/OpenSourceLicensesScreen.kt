package app.yokaicore.presentation.more.settings.screen.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.m3.util.htmlReadyLicenseContent
import app.yokaicore.presentation.components.AppBar
import app.yokaicore.presentation.util.Screen
import yokaicore.i18n.MR
import yokaicore.presentation.core.components.material.Scaffold
import yokaicore.presentation.core.i18n.stringResource

class OpenSourceLicensesScreen : Screen() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = { scrollBehavior ->
                AppBar(
                    title = stringResource(MR.strings.licenses),
                    navigateUp = navigator::pop,
                    scrollBehavior = scrollBehavior,
                )
            },
        ) { contentPadding ->
            LibrariesContainer(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = contentPadding,
                onLibraryClick = {
                    val libraryLicenseScreen = OpenSourceLibraryLicenseScreen(
                        name = it.library.name,
                        website = it.library.website,
                        license = it.library.licenses.firstOrNull()?.htmlReadyLicenseContent.orEmpty(),
                    )
                    navigator.push(libraryLicenseScreen)
                },
            )
        }
    }
}
