package mozhde.shams.features.home.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import javax.inject.Inject
import javax.inject.Provider

class PagerFactory @Inject constructor(
    private val pagingConfig: PagingConfig,
    private val moviePagingSourceProvider: Provider<MoviePagingSource>,
) {
    fun create() = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            moviePagingSourceProvider.get()
        }
    )
}