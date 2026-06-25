package yokaicore.domain.category.interactor

import logcat.LogPriority
import yokaicore.core.util.lang.withNonCancellableContext
import yokaicore.core.util.system.logcat
import yokaicore.domain.category.model.Category
import yokaicore.domain.category.model.CategoryUpdate
import yokaicore.domain.category.repository.CategoryRepository

class RenameCategory(
    private val categoryRepository: CategoryRepository,
) {

    suspend fun await(categoryId: Long, name: String) = withNonCancellableContext {
        val update = CategoryUpdate(
            id = categoryId,
            name = name,
        )

        try {
            categoryRepository.updatePartial(update)
            Result.Success
        } catch (e: Exception) {
            logcat(LogPriority.ERROR, e)
            Result.InternalError(e)
        }
    }

    suspend fun await(category: Category, name: String) = await(category.id, name)

    sealed interface Result {
        data object Success : Result
        data class InternalError(val error: Throwable) : Result
    }
}
