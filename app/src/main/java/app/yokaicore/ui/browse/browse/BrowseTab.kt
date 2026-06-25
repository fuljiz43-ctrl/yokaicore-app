package app.yokaicore.ui.browse

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import app.yokaicore.presentation.components.TabbedScreen
import app.yokaicore.presentation.util.Tab
import app.yokaicore.R
import app.yokaicore.ui.browse.extension.ExtensionsScreenModel
import app.yokaicore.ui.browse.extension.extensionsTab
import app.yokaicore.ui.browse.migration.sources.migrateSourceTab
import app.yokaicore.ui.browse.source.globalsearch.GlobalSearchScreen
import app.yokaicore.ui.browse.source.sourcesTab
import app.yokaicore.ui.main.MainActivity
import kotlinx.collections.immutable.persistentListOf
import yokaicore.i18n.MR
import yokaicore.presentation.core.i18n.stringResource

data class BrowseTab(
    private val toExtensions: Boolean = false,
) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val isSelected = LocalTabNavigator.current.current.key == key
            val image = AnimatedImageVector.animatedVectorResource(R.drawable.anim_browse_enter)
            return TabOptions(
                index = 3u,
                title = stringResource(MR.strings.browse),
                icon = rememberAnimatedVectorPainter(image, isSelected),
            )
        }

    override suspend fun onReselect(navigator: Navigator) {
        navigator.push(GlobalSearchScreen())
    }

    @Composable
    override fun Content() {
        val context = LocalContext.current

        // Hoisted for extensions tab's search bar
        val extensionsScreenModel = rememberScreenModel { ExtensionsScreenModel() }
        val extensionsState by extensionsScreenModel.state.collectAsState()

        TabbedScreen(
            titleRes = MR.strings.browse,
            tabs = persistentListOf(
                sourcesTab(),
                extensionsTab(extensionsScreenModel),
                migrateSourceTab(),
            ),
            startIndex = 1.takeIf { toExtensions },
            searchQuery = extensionsState.searchQuery,
            onChangeSearchQuery = extensionsScreenModel::search,
        )

        LaunchedEffect(Unit) {
            (context as? MainActivity)?.ready = true
        }
    }
}
