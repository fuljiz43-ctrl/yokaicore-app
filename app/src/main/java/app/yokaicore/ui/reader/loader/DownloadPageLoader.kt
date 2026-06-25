package app.yokaicore.ui.reader.loader

import android.app.Application
import android.net.Uri
import com.hippo.unifile.UniFile
import app.yokaicore.data.database.models.toDomainChapter
import app.yokaicore.data.download.DownloadManager
import app.yokaicore.data.download.DownloadProvider
import app.yokaicore.source.Source
import app.yokaicore.source.model.Page
import app.yokaicore.ui.reader.model.ReaderChapter
import app.yokaicore.ui.reader.model.ReaderPage
import yokaicore.core.storage.UniFileTempFileManager
import yokaicore.domain.manga.model.Manga
import uy.kohesive.injekt.injectLazy

/**
 * Loader used to load a chapter from the downloaded chapters.
 */
internal class DownloadPageLoader(
    private val chapter: ReaderChapter,
    private val manga: Manga,
    private val source: Source,
    private val downloadManager: DownloadManager,
    private val downloadProvider: DownloadProvider,
    private val tempFileManager: UniFileTempFileManager,
) : PageLoader() {

    private val context: Application by injectLazy()

    private var zipPageLoader: ZipPageLoader? = null

    override var isLocal: Boolean = true

    override suspend fun getPages(): List<ReaderPage> {
        val dbChapter = chapter.chapter
        val chapterPath = downloadProvider.findChapterDir(dbChapter.name, dbChapter.scanlator, manga.title, source)
        return if (chapterPath?.isFile == true) {
            getPagesFromArchive(chapterPath)
        } else {
            getPagesFromDirectory()
        }
    }

    override fun recycle() {
        super.recycle()
        zipPageLoader?.recycle()
    }

    private suspend fun getPagesFromArchive(file: UniFile): List<ReaderPage> {
        val loader = ZipPageLoader(tempFileManager.createTempFile(file)).also { zipPageLoader = it }
        return loader.getPages()
    }

    private fun getPagesFromDirectory(): List<ReaderPage> {
        val pages = downloadManager.buildPageList(source, manga, chapter.chapter.toDomainChapter()!!)
        return pages.map { page ->
            ReaderPage(page.index, page.url, page.imageUrl) {
                context.contentResolver.openInputStream(page.uri ?: Uri.EMPTY)!!
            }.apply {
                status = Page.State.READY
            }
        }
    }

    override suspend fun loadPage(page: ReaderPage) {
        zipPageLoader?.loadPage(page)
    }
}
