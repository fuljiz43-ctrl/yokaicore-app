package app.yokaicore.ui.reader.loader

import app.yokaicore.source.model.Page
import app.yokaicore.ui.reader.model.ReaderPage
import app.yokaicore.util.storage.EpubFile
import java.io.File

/**
 * Loader used to load a chapter from a .epub file.
 */
internal class EpubPageLoader(file: File) : PageLoader() {

    private val epub = EpubFile(file)

    override var isLocal: Boolean = true

    override suspend fun getPages(): List<ReaderPage> {
        return epub.getImagesFromPages()
            .mapIndexed { i, path ->
                val streamFn = { epub.getInputStream(epub.getEntry(path)!!) }
                ReaderPage(i).apply {
                    stream = streamFn
                    status = Page.State.READY
                }
            }
    }

    override suspend fun loadPage(page: ReaderPage) {
        check(!isRecycled)
    }

    override fun recycle() {
        super.recycle()
        epub.close()
    }
}
