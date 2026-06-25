package app.yokaicore.domain.extension.model

import app.yokaicore.extension.model.Extension

data class Extensions(
    val updates: List<Extension.Installed>,
    val installed: List<Extension.Installed>,
    val available: List<Extension.Available>,
    val untrusted: List<Extension.Untrusted>,
)
