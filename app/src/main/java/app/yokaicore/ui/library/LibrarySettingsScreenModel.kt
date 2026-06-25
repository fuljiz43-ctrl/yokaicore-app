package app.yokaicore.ui.library

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import app.yokaicore.domain.base.BasePreferences
import app.yokaicore.data.track.TrackerManager
import yokaicore.core.preference.Preference
import yokaicore.core.preference.TriState
import yokaicore.core.preference.getAndSet
import yokaicore.core.util.lang.launchIO
import yokaicore.domain.category.interactor.SetDisplayMode
import yokaicore.domain.category.interactor.SetSortModeForCategory
import yokaicore.domain.category.model.Category
import yokaicore.domain.library.model.LibraryDisplayMode
import yokaicore.domain.library.model.LibrarySort
import yokaicore.domain.library.service.LibraryPreferences
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class LibrarySettingsScreenModel(
    val preferences: BasePreferences = Injekt.get(),
    val libraryPreferences: LibraryPreferences = Injekt.get(),
    private val setDisplayMode: SetDisplayMode = Injekt.get(),
    private val setSortModeForCategory: SetSortModeForCategory = Injekt.get(),
    private val trackerManager: TrackerManager = Injekt.get(),
) : ScreenModel {

    val trackers
        get() = trackerManager.trackers.filter { it.isLoggedIn }

    fun toggleFilter(preference: (LibraryPreferences) -> Preference<TriState>) {
        preference(libraryPreferences).getAndSet {
            it.next()
        }
    }

    fun toggleTracker(id: Int) {
        toggleFilter { libraryPreferences.filterTracking(id) }
    }

    fun setDisplayMode(mode: LibraryDisplayMode) {
        setDisplayMode.await(mode)
    }

    fun setSort(category: Category?, mode: LibrarySort.Type, direction: LibrarySort.Direction) {
        screenModelScope.launchIO {
            setSortModeForCategory.await(category, mode, direction)
        }
    }
}
