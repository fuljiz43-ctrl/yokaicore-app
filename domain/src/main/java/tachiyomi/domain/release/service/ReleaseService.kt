package yokaicore.domain.release.service

import yokaicore.domain.release.model.Release

interface ReleaseService {

    suspend fun latest(repository: String): Release
}
