package app.yokaicore.data.backup.create.creators

import app.yokaicore.data.backup.models.BackupCategory
import app.yokaicore.data.backup.models.backupCategoryMapper
import yokaicore.domain.category.interactor.GetCategories
import yokaicore.domain.category.model.Category
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

class CategoriesBackupCreator(
    private val getCategories: GetCategories = Injekt.get(),
) {

    suspend fun backupCategories(): List<BackupCategory> {
        return getCategories.await()
            .filterNot(Category::isSystemCategory)
            .map(backupCategoryMapper)
    }
}
