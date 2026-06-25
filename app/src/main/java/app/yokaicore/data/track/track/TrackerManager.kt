package app.yokaicore.data.track

import app.yokaicore.data.track.anilist.Anilist
import app.yokaicore.data.track.bangumi.Bangumi
import app.yokaicore.data.track.kavita.Kavita
import app.yokaicore.data.track.kitsu.Kitsu
import app.yokaicore.data.track.komga.Komga
import app.yokaicore.data.track.mangaupdates.MangaUpdates
import app.yokaicore.data.track.myanimelist.MyAnimeList
import app.yokaicore.data.track.shikimori.Shikimori
import app.yokaicore.data.track.suwayomi.Suwayomi

class TrackerManager {

    companion object {
        const val ANILIST = 2L
        const val KITSU = 3L
        const val KAVITA = 8L
    }

    val myAnimeList = MyAnimeList(1L)
    val aniList = Anilist(ANILIST)
    val kitsu = Kitsu(KITSU)
    val shikimori = Shikimori(4L)
    val bangumi = Bangumi(5L)
    val komga = Komga(6L)
    val mangaUpdates = MangaUpdates(7L)
    val kavita = Kavita(KAVITA)
    val suwayomi = Suwayomi(9L)

    val trackers = listOf(myAnimeList, aniList, kitsu, shikimori, bangumi, komga, mangaUpdates, kavita, suwayomi)

    fun loggedInTrackers() = trackers.filter { it.isLoggedIn }

    fun get(id: Long) = trackers.find { it.id == id }
}
