package app.yokaicore.ui.webview

import android.content.Context
import androidx.core.net.toUri
import cafe.adriel.voyager.core.model.StateScreenModel
import app.yokaicore.presentation.more.stats.StatsScreenState
import app.yokaicore.network.NetworkHelper
import app.yokaicore.source.online.HttpSource
import app.yokaicore.util.system.openInBrowser
import app.yokaicore.util.system.toShareIntent
import app.yokaicore.util.system.toast
import logcat.LogPriority
import okhttp3.HttpUrl.Companion.toHttpUrl
import yokaicore.core.util.system.logcat
import yokaicore.domain.source.service.SourceManager
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class WebViewScreenModel(
    val sourceId: Long?,
    private val sourceManager: SourceManager = Injekt.get(),
    private val network: NetworkHelper = Injekt.get(),
) : StateScreenModel<StatsScreenState>(StatsScreenState.Loading) {

    var headers = emptyMap<String, String>()

    init {
        sourceId?.let { sourceManager.get(it) as? HttpSource }?.let { source ->
            try {
                headers = source.headers.toMultimap().mapValues { it.value.getOrNull(0) ?: "" }
            } catch (e: Exception) {
                logcat(LogPriority.ERROR, e) { "Failed to build headers" }
            }
        }
    }

    fun shareWebpage(context: Context, url: String) {
        try {
            context.startActivity(url.toUri().toShareIntent(context, type = "text/plain"))
        } catch (e: Exception) {
            context.toast(e.message)
        }
    }

    fun openInBrowser(context: Context, url: String) {
        context.openInBrowser(url, forceDefaultBrowser = true)
    }

    fun clearCookies(url: String) {
        val cleared = network.cookieJar.remove(url.toHttpUrl())
        logcat { "Cleared $cleared cookies for: $url" }
    }
}
