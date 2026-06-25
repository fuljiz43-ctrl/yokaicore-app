package yokaicore.domain.manga.interactor

import kotlinx.coroutines.flow.Flow
import yokaicore.domain.manga.model.Manga
import yokaicore.domain.manga.repository.MangaRepository

class GetFavorites(
    private val mangaRepository: MangaRepository,
) {

    suspend fun await(): List<Manga> {
        return mangaRepository.getFavorites()
    }

    fun subscribe(sourceId: Long): Flow<List<Manga>> {
        return mangaRepository.getFavoritesBySourceId(sourceId)
    }
}
