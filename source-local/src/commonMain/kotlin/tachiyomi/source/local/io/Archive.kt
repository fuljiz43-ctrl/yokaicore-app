package yokaicore.source.local.io

import com.hippo.unifile.UniFile
import yokaicore.core.storage.extension

object Archive {

    private val SUPPORTED_ARCHIVE_TYPES = listOf("zip", "cbz", "rar", "cbr", "epub")

    fun isSupported(file: UniFile): Boolean {
        return file.extension in SUPPORTED_ARCHIVE_TYPES
    }
}
