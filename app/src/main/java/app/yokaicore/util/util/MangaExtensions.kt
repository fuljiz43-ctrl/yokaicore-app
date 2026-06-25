package app.yokaicore.util

import app.yokaicore.domain.manga.interactor.UpdateManga
import app.yokaicore.domain.manga.model.hasCustomCover
import app.yokaicore.domain.manga.model.toSManga
import app.yokaicore.data.cache.CoverCache
import app.yokaicore.source.model.SManga
import yokaicore.domain.download.service.DownloadPreferences
import yokaicore.domain.manga.model.Manga
import yokaicore.source.local.image.LocalCoverManager
import yokaicore.source.local.isLocal
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.io.InputStream
import java.time.Instant

/**
 * Call before updating [Manga.thumbnail_url] to ensure old cover can be cleared from cache
 */
fun Manga.prepUpdateCover(coverCache: CoverCache, remoteManga: SManga, refreshSameUrl: Boolean): Manga {
    // Never refresh covers if the new url is null, as the current url has possibly become invalid
    val newUrl = remoteManga.thumbnail_url ?: return this

    // Never refresh covers if the url is empty to avoid "losing" existing covers
    if (newUrl.isEmpty()) return this

    if (!refreshSameUrl && thumbnailUrl == newUrl) return this

    return when {
        isLocal() -> {
            this.copy(coverLastModified = Instant.now().toEpochMilli())
        }
        hasCustomCover(coverCache) -> {
            coverCache.deleteFromCache(this, false)
            this
        }
        else -> {
            coverCache.deleteFromCache(this, false)
            this.copy(coverLastModified = Instant.now().toEpochMilli())
        }
    }
}

fun Manga.removeCovers(coverCache: CoverCache = Injekt.get()): Manga {
    if (isLocal()) return this
    return if (coverCache.deleteFromCache(this, true) > 0) {
        return copy(coverLastModified = Instant.now().toEpochMilli())
    } else {
        this
    }
}

fun Manga.shouldDownloadNewChapters(dbCategories: List<Long>, preferences: DownloadPreferences): Boolean {
    if (!favorite) return false

    val categories = dbCategories.ifEmpty { listOf(0L) }

    // Boolean to determine if user wants to automatically download new chapters.
    val downloadNewChapters = preferences.downloadNewChapters().get()
    if (!downloadNewChapters) return false

    val includedCategories = preferences.downloadNewChapterCategories().get().map { it.toLong() }
    val excludedCategories = preferences.downloadNewChapterCategoriesExclude().get().map { it.toLong() }

    // Default: Download from all categories
    if (includedCategories.isEmpty() && excludedCategories.isEmpty()) return true

    // In excluded category
    if (categories.any { it in excludedCategories }) return false

    // Included category not selected
    if (includedCategories.isEmpty()) return true

    // In included category
    return categories.any { it in includedCategories }
}

suspend fun Manga.editCover(
    coverManager: LocalCoverManager,
    stream: InputStream,
    updateManga: UpdateManga = Injekt.get(),
    coverCache: CoverCache = Injekt.get(),
) {
    if (isLocal()) {
        coverManager.update(toSManga(), stream)
        updateManga.awaitUpdateCoverLastModified(id)
    } else if (favorite) {
        coverCache.setCustomCoverToCache(this, stream)
        updateManga.awaitUpdateCoverLastModified(id)
    }
}
