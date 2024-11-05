package mozhde.shams.features.home.data

import mozhde.shams.features.home.domain.MovieList
import mozhde.shams.features.home.domain.MovieListRepository
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieListService: MovieListService,
) : MovieListRepository {

    override suspend fun getMovieList(
        movieTye: String,
        page: Int
    ): Result<MovieList> {
        return movieListService.getMovieList(
            movieTye = movieTye,
            page = page,
        ).mapCatching { it.toDomain() }
    }
}