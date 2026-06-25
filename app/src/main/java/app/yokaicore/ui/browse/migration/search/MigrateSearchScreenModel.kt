package app.yokaicore.ui.browse.migration.search

import cafe.adriel.voyager.core.model.screenModelScope
import app.yokaicore.source.CatalogueSource
import app.yokaicore.ui.browse.source.globalsearch.SearchScreenModel
import app.yokaicore.ui.browse.source.globalsearch.SourceFilter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import yokaicore.domain.manga.interactor.GetManga
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class MigrateSearchScreenModel(
    val mangaId: Long,
    getManga: GetManga = Injekt.get(),
) : SearchScreenModel() {

    init {
        screenModelScope.launch {
            val manga = getManga.await(mangaId)!!
            mutableState.update {
                it.copy(
                    fromSourceId = manga.source,
                    searchQuery = manga.title,
                )
            }
            search()
        }
    }

    override fun getEnabledSources(): List<CatalogueSource> {
        return super.getEnabledSources()
            .filter { state.value.sourceFilter != SourceFilter.PinnedOnly || "${it.id}" in pinnedSources }
            .sortedWith(
                compareBy(
                    { it.id != state.value.fromSourceId },
                    { "${it.id}" !in pinnedSources },
                    { "${it.name.lowercase()} (${it.lang})" },
                ),
            )
    }
}
