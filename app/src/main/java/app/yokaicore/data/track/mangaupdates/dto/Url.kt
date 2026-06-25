package app.yokaicore.data.track.mangaupdates.dto

import kotlinx.serialization.Serializable

@Serializable
data class Url(
    val original: String? = null,
    val thumb: String? = null,
)
