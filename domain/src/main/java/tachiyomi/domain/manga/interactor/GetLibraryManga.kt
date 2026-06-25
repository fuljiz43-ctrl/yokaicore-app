package yokaicore.domain.manga.interactor

import kotlinx.coroutines.flow.Flow
import yokaicore.domain.library.model.LibraryManga
import yokaicore.domain.manga.repository.MangaRepository

class GetLibraryManga(
    private val mangaRepository: MangaRepository,
) {

    suspend fun await(): List<LibraryManga> {
        return mangaRepository.getLibraryManga()
    }

    fun subscribe(): Flow<List<LibraryManga>> {
        return mangaRepository.getLibraryMangaAsFlow()
    }
}
