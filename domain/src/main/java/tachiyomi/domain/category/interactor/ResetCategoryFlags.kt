package yokaicore.domain.category.interactor

import yokaicore.domain.category.repository.CategoryRepository
import yokaicore.domain.library.model.plus
import yokaicore.domain.library.service.LibraryPreferences

class ResetCategoryFlags(
    private val preferences: LibraryPreferences,
    private val categoryRepository: CategoryRepository,
) {

    suspend fun await() {
        val sort = preferences.sortingMode().get()
        categoryRepository.updateAllFlags(sort.type + sort.direction)
    }
}
