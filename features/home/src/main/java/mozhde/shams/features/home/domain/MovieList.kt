package mozhde.shams.features.home.domain

data class MovieList(
    val movies: List<Movie>,
    val page: Int,
    val totalPages: Int,
)

data class Movie(
    val title: String?,
    val overview: String?,
    val posterPath: String?,
)