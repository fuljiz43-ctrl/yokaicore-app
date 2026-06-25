package yokaicore.domain.source.interactor

import app.yokaicore.source.model.FilterList
import yokaicore.domain.source.repository.SourcePagingSourceType
import yokaicore.domain.source.repository.SourceRepository

class GetRemoteManga(
    private val repository: SourceRepository,
) {

    fun subscribe(sourceId: Long, query: String, filterList: FilterList): SourcePagingSourceType {
        return when (query) {
            QUERY_POPULAR -> repository.getPopular(sourceId)
            QUERY_LATEST -> repository.getLatest(sourceId)
            else -> repository.search(sourceId, query, filterList)
        }
    }

    companion object {
        const val QUERY_POPULAR = "app.yokaicore.domain.source.interactor.POPULAR"
        const val QUERY_LATEST = "app.yokaicore.domain.source.interactor.LATEST"
    }
}
