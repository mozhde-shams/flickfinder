package mozhde.shams.features.home.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieListService {

    @GET("movie/{movieType}")
    suspend fun getMovieList(
        @Path("movieType") movieType: String = "now_playing",
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): JsonMovieList
}