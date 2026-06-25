package app.yokaicore.data.track.mangaupdates.dto

import app.yokaicore.data.database.models.Track
import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val rating: Float? = null,
)

fun Rating.copyTo(track: Track): Track {
    return track.apply {
        this.score = rating ?: 0f
    }
}
