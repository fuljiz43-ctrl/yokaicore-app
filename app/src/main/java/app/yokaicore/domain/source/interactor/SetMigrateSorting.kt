package app.yokaicore.domain.source.interactor

import app.yokaicore.domain.source.service.SourcePreferences

class SetMigrateSorting(
    private val preferences: SourcePreferences,
) {

    fun await(mode: Mode, direction: Direction) {
        preferences.migrationSortingMode().set(mode)
        preferences.migrationSortingDirection().set(direction)
    }

    enum class Mode {
        ALPHABETICAL,
        TOTAL,
    }

    enum class Direction {
        ASCENDING,
        DESCENDING,
    }
}
