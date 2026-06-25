package yokaicore.data.release

import app.yokaicore.network.GET
import app.yokaicore.network.NetworkHelper
import app.yokaicore.network.awaitSuccess
import app.yokaicore.network.parseAs
import kotlinx.serialization.json.Json
import yokaicore.domain.release.model.Release
import yokaicore.domain.release.service.ReleaseService

class ReleaseServiceImpl(
    private val networkService: NetworkHelper,
    private val json: Json,
) : ReleaseService {

    override suspend fun latest(repository: String): Release {
        return with(json) {
            networkService.client
                .newCall(GET("https://api.github.com/repos/$repository/releases/latest"))
                .awaitSuccess()
                .parseAs<GithubRelease>()
                .let(releaseMapper)
        }
    }
}
