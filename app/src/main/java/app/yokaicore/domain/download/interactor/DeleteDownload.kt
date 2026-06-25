package app.yokaicore.domain.download.interactor

import app.yokaicore.data.download.DownloadManager
import yokaicore.core.util.lang.withNonCancellableContext
import yokaicore.domain.chapter.model.Chapter
import yokaicore.domain.manga.model.Manga
import yokaicore.domain.source.service.SourceManager

class DeleteDownload(
    private val sourceManager: SourceManager,
    private val downloadManager: DownloadManager,
) {

    suspend fun awaitAll(manga: Manga, vararg chapters: Chapter) = withNonCancellableContext {
        sourceManager.get(manga.source)?.let { source ->
            downloadManager.deleteChapters(chapters.toList(), manga, source)
        }
    }
}
