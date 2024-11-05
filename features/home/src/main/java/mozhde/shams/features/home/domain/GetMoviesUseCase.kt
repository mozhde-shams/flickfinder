package mozhde.shams.features.home.domain

import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieListRepository: MovieListRepository
){
    suspend operator fun invoke(
        movieType:String,
        page: Int,
    ): Result<MovieList> = movieListRepository.getMovieList(
        movieTye = movieType,
        page = page,
    )
}