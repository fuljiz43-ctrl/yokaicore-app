package yokaicore.domain.source.repository

import androidx.paging.PagingSource
import app.yokaicore.source.model.FilterList
import app.yokaicore.source.model.SManga
import kotlinx.coroutines.flow.Flow
import yokaicore.domain.source.model.Source
import yokaicore.domain.source.model.SourceWithCount

typealias SourcePagingSourceType = PagingSource<Long, SManga>

interface SourceRepository {

    fun getSources(): Flow<List<Source>>

    fun getOnlineSources(): Flow<List<Source>>

    fun getSourcesWithFavoriteCount(): Flow<List<Pair<Source, Long>>>

    fun getSourcesWithNonLibraryManga(): Flow<List<SourceWithCount>>

    fun search(sourceId: Long, query: String, filterList: FilterList): SourcePagingSourceType

    fun getPopular(sourceId: Long): SourcePagingSourceType

    fun getLatest(sourceId: Long): SourcePagingSourceType
}
