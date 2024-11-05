package mozhde.shams.features.home.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mozhde.shams.features.home.domain.Movie
import mozhde.shams.features.home.domain.MovieList

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w780/"

@Serializable
data class JsonMovieList(
    @SerialName("dates")
    val dates: Dates,
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<ResultList>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)

@Serializable
data class Dates(
    @SerialName("maximum")
    val maximum: String,
    @SerialName("minimum")
    val minimum: String
)

@Serializable
data class ResultList(
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("genre_ids")
    val genreIds: List<Int>?,
    @SerialName("id")
    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_title")
    val originalTitle: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("title")
    val title: String?,
    @SerialName("video")
    val video: Boolean,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
)

internal fun JsonMovieList.toDomain() = MovieList(
    movies = results.map {
        it.toDomain()
    },
    page = page,
    totalPages = totalPages,
)

internal fun ResultList.toDomain() = Movie(
    title = title,
    overview = overview,
    posterPath = POSTER_BASE_URL.plus(posterPath),
)