package app.yokaicore.domain.extension.interactor

import app.yokaicore.domain.source.service.SourcePreferences
import kotlinx.coroutines.flow.Flow

class GetExtensionRepos(private val preferences: SourcePreferences) {

    fun subscribe(): Flow<Set<String>> {
        return preferences.extensionRepos().changes()
    }
}
