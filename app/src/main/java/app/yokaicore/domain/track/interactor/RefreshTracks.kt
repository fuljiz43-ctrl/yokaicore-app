package app.yokaicore.domain.track.interactor

import app.yokaicore.domain.track.model.toDbTrack
import app.yokaicore.domain.track.model.toDomainTrack
import app.yokaicore.data.track.Tracker
import app.yokaicore.data.track.TrackerManager
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import yokaicore.domain.track.interactor.GetTracks
import yokaicore.domain.track.interactor.InsertTrack

class RefreshTracks(
    private val getTracks: GetTracks,
    private val trackerManager: TrackerManager,
    private val insertTrack: InsertTrack,
    private val syncChapterProgressWithTrack: SyncChapterProgressWithTrack,
) {

    /**
     * Fetches updated tracking data from all logged in trackers.
     *
     * @return Failed updates.
     */
    suspend fun await(mangaId: Long): List<Pair<Tracker?, Throwable>> {
        return supervisorScope {
            return@supervisorScope getTracks.await(mangaId)
                .map { it to trackerManager.get(it.trackerId) }
                .filter { (_, service) -> service?.isLoggedIn == true }
                .map { (track, service) ->
                    async {
                        return@async try {
                            val updatedTrack = service!!.refresh(track.toDbTrack())
                            insertTrack.await(updatedTrack.toDomainTrack()!!)
                            syncChapterProgressWithTrack.await(mangaId, track, service)
                            null
                        } catch (e: Throwable) {
                            service to e
                        }
                    }
                }
                .awaitAll()
                .filterNotNull()
        }
    }
}
