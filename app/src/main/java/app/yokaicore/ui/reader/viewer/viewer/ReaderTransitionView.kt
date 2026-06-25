package app.yokaicore.ui.reader.viewer

import android.content.Context
import android.util.AttributeSet
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView
import app.yokaicore.presentation.reader.ChapterTransition
import app.yokaicore.presentation.theme.YokaiCoreTheme
import app.yokaicore.data.download.DownloadManager
import app.yokaicore.ui.reader.model.ChapterTransition
import yokaicore.domain.manga.model.Manga
import yokaicore.source.local.isLocal

class ReaderTransitionView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    AbstractComposeView(context, attrs) {

    private var data: Data? by mutableStateOf(null)

    init {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    fun bind(transition: ChapterTransition, downloadManager: DownloadManager, manga: Manga?) {
        data = if (manga != null) {
            Data(
                transition = transition,
                currChapterDownloaded = transition.from.pageLoader?.isLocal == true,
                goingToChapterDownloaded = manga.isLocal() || transition.to?.chapter?.let { goingToChapter ->
                    downloadManager.isChapterDownloaded(
                        chapterName = goingToChapter.name,
                        chapterScanlator = goingToChapter.scanlator,
                        mangaTitle = manga.title,
                        sourceId = manga.source,
                        skipCache = true,
                    )
                } ?: false,
            )
        } else {
            null
        }
    }

    @Composable
    override fun Content() {
        data?.let {
            YokaiCoreTheme {
                CompositionLocalProvider(
                    LocalTextStyle provides MaterialTheme.typography.bodySmall,
                    LocalContentColor provides MaterialTheme.colorScheme.onBackground,
                ) {
                    ChapterTransition(
                        transition = it.transition,
                        currChapterDownloaded = it.currChapterDownloaded,
                        goingToChapterDownloaded = it.goingToChapterDownloaded,
                    )
                }
            }
        }
    }

    private data class Data(
        val transition: ChapterTransition,
        val currChapterDownloaded: Boolean,
        val goingToChapterDownloaded: Boolean,
    )
}
