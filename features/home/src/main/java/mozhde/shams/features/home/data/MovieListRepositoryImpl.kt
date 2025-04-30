package mozhde.shams.features.home.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import mozhde.shams.features.home.domain.Movie
import mozhde.shams.features.home.domain.MovieListRepository
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val pagerFactory: PagerFactory,
) : MovieListRepository {

    override suspend fun getPagedMovies(): Flow<PagingData<Movie>> {
        return pagerFactory.create().flow
    }
}