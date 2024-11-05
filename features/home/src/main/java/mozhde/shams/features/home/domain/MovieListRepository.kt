package mozhde.shams.features.home.domain

interface MovieListRepository {
    suspend fun getMovieList(
        movieTye: String,
        page: Int,
    ): Result<MovieList>
}