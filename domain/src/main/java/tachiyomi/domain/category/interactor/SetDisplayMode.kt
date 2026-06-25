package yokaicore.domain.category.interactor

import yokaicore.domain.library.model.LibraryDisplayMode
import yokaicore.domain.library.service.LibraryPreferences

class SetDisplayMode(
    private val preferences: LibraryPreferences,
) {

    fun await(display: LibraryDisplayMode) {
        preferences.displayMode().set(display)
    }
}
