package yokaicore.domain.source.interactor

import kotlinx.coroutines.flow.Flow
import yokaicore.domain.source.model.SourceWithCount
import yokaicore.domain.source.repository.SourceRepository

class GetSourcesWithNonLibraryManga(
    private val repository: SourceRepository,
) {

    fun subscribe(): Flow<List<SourceWithCount>> {
        return repository.getSourcesWithNonLibraryManga()
    }
}
