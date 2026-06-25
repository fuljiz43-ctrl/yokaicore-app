package yokaicore.domain.history.interactor

import kotlinx.coroutines.flow.Flow
import yokaicore.domain.history.model.History
import yokaicore.domain.history.model.HistoryWithRelations
import yokaicore.domain.history.repository.HistoryRepository

class GetHistory(
    private val repository: HistoryRepository,
) {

    suspend fun await(mangaId: Long): List<History> {
        return repository.getHistoryByMangaId(mangaId)
    }

    fun subscribe(query: String): Flow<List<HistoryWithRelations>> {
        return repository.getHistory(query)
    }
}
