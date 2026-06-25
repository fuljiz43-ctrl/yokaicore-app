package yokaicore.domain.source.service

import app.yokaicore.source.CatalogueSource
import app.yokaicore.source.Source
import app.yokaicore.source.online.HttpSource
import kotlinx.coroutines.flow.Flow
import yokaicore.domain.source.model.StubSource

interface SourceManager {

    val catalogueSources: Flow<List<CatalogueSource>>

    fun get(sourceKey: Long): Source?

    fun getOrStub(sourceKey: Long): Source

    fun getOnlineSources(): List<HttpSource>

    fun getCatalogueSources(): List<CatalogueSource>

    fun getStubSources(): List<StubSource>
}
