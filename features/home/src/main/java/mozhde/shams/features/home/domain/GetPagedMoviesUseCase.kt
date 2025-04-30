package mozhde.shams.features.home.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedMoviesUseCase @Inject constructor(
    private val movieListRepository: MovieListRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Movie>> = movieListRepository.getPagedMovies()
}