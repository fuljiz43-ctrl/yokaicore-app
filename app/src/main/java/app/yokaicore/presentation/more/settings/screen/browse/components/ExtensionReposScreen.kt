@file:JvmName("ExtensionReposScreenKt")

package app.yokaicore.presentation.more.settings.screen.browse.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.yokaicore.presentation.category.components.CategoryFloatingActionButton
import app.yokaicore.presentation.components.AppBar
import app.yokaicore.presentation.more.settings.screen.browse.RepoScreenState
import yokaicore.i18n.MR
import yokaicore.presentation.core.components.material.Scaffold
import yokaicore.presentation.core.components.material.padding
import yokaicore.presentation.core.components.material.topSmallPaddingValues
import yokaicore.presentation.core.i18n.stringResource
import yokaicore.presentation.core.screens.EmptyScreen
import yokaicore.presentation.core.util.plus

@Composable
fun ExtensionReposScreen(
    state: RepoScreenState.Success,
    onClickCreate: () -> Unit,
    onClickDelete: (String) -> Unit,
    navigateUp: () -> Unit,
) {
    val lazyListState = rememberLazyListState()
    Scaffold(
        topBar = { scrollBehavior ->
            AppBar(
                navigateUp = navigateUp,
                title = stringResource(MR.strings.label_extension_repos),
                scrollBehavior = scrollBehavior,
            )
        },
        floatingActionButton = {
            CategoryFloatingActionButton(
                lazyListState = lazyListState,
                onCreate = onClickCreate,
            )
        },
    ) { paddingValues ->
        if (state.isEmpty) {
            EmptyScreen(
                MR.strings.information_empty_repos,
                modifier = Modifier.padding(paddingValues),
            )
            return@Scaffold
        }

        ExtensionReposContent(
            repos = state.repos,
            lazyListState = lazyListState,
            paddingValues = paddingValues + topSmallPaddingValues +
                PaddingValues(horizontal = MaterialTheme.padding.medium),
            onClickDelete = onClickDelete,
        )
    }
}
