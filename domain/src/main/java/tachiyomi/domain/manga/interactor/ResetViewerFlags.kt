package yokaicore.domain.manga.interactor

import yokaicore.domain.manga.repository.MangaRepository

class ResetViewerFlags(
    private val mangaRepository: MangaRepository,
) {

    suspend fun await(): Boolean {
        return mangaRepository.resetViewerFlags()
    }
}
