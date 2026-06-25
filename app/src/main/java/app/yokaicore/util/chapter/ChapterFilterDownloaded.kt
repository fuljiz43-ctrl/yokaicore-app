package app.yokaicore.util.chapter

import app.yokaicore.data.download.DownloadCache
import yokaicore.domain.chapter.model.Chapter
import yokaicore.domain.manga.model.Manga
import yokaicore.source.local.isLocal
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Returns a copy of the list with not downloaded chapters removed.
 */
fun List<Chapter>.filterDownloaded(manga: Manga): List<Chapter> {
    if (manga.isLocal()) return this

    val downloadCache: DownloadCache = Injekt.get()

    return filter { downloadCache.isChapterDownloaded(it.name, it.scanlator, manga.title, manga.source, false) }
}
