package mozhde.shams.features.home.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieListService {

    @GET("movie/{movieType}")
    suspend fun getMovieList(
        @Path("movieType") movieTye: String,
        @Query("api_key") apiKey: String = "YOUR_API_KEY",
        @Query("page") page: Int,
    ): Result<JsonMovieList>
}