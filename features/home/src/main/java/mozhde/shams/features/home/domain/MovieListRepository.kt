package mozhde.shams.features.home.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getPagedMovies(): Flow<PagingData<Movie>>
}