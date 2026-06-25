package yokaicore.domain.history.interactor

import yokaicore.domain.history.model.HistoryUpdate
import yokaicore.domain.history.repository.HistoryRepository

class UpsertHistory(
    private val historyRepository: HistoryRepository,
) {

    suspend fun await(historyUpdate: HistoryUpdate) {
        historyRepository.upsertHistory(historyUpdate)
    }
}
