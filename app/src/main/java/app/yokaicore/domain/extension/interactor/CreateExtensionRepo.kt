package app.yokaicore.domain.extension.interactor

import app.yokaicore.domain.source.service.SourcePreferences
import yokaicore.core.preference.plusAssign

class CreateExtensionRepo(private val preferences: SourcePreferences) {

    fun await(name: String): Result {
        // Do not allow invalid formats
        if (!name.matches(repoRegex)) {
            return Result.InvalidUrl
        }

        preferences.extensionRepos() += name.removeSuffix("/index.min.json")

        return Result.Success
    }

    sealed interface Result {
        data object InvalidUrl : Result
        data object Success : Result
    }
}

private val repoRegex = """^https://.*/index\.min\.json$""".toRegex()
