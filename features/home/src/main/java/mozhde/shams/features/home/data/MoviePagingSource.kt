package mozhde.shams.features.home.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import mozhde.shams.features.home.domain.Movie
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movieListService: MovieListService,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: 1
        return try {
            val movieList = movieListService.getMovieList(
                page = position,
            )

            val movies = movieList.toDomain().movies

            LoadResult.Page(
                data = movies,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1,
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

}