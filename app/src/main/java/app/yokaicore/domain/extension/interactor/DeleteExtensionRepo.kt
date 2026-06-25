package app.yokaicore.domain.extension.interactor

import app.yokaicore.domain.source.service.SourcePreferences
import yokaicore.core.preference.minusAssign

class DeleteExtensionRepo(private val preferences: SourcePreferences) {

    fun await(repo: String) {
        preferences.extensionRepos() -= repo
    }
}
