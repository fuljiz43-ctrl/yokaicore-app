package app.yokaicore.data.track

import yokaicore.domain.track.model.Track

/**
 * Tracker that support deleting am entry from a user's list.
 */
interface DeletableTracker {

    suspend fun delete(track: Track)
}
