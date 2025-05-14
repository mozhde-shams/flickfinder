package mozhde.shams.features.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import mozhde.shams.features.home.domain.Movie
import mozhde.shams.features.home.ui.HomeEvent
import mozhde.shams.features.home.ui.HomeScreen
import mozhde.shams.features.home.ui.HomeState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displayLoadingWhenStateIsLoading() {

        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(
                    status = HomeState.Status.Loading,
                ),
                dispatch = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Loading")
            .assertIsDisplayed()

    }

    @Test
    fun homeScreen_displayErrorScreenWhenStateIsError() {

        var tryAgainClicked = false
        val dispatch: (HomeEvent) -> Unit = { event ->
            if (event is HomeEvent.ErrorScreenTryAgainClicked)
                tryAgainClicked = true
        }
        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(
                    status = HomeState.Status.Error
                ),
                dispatch = dispatch,
            )
        }

        composeTestRule
            .onNodeWithText("Seems like something went wrong.")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Try again")
            .assertIsDisplayed()
            .performClick()

        assert(tryAgainClicked)
    }

    @Test
    fun homeScreen_displayMoviesWhenStateIsMovies() {

        val pagingDataFlow: Flow<PagingData<Movie>> = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { FakeMoviePagingSource() }
        ).flow

        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(
                    status = HomeState.Status.Movies,
                    movies = pagingDataFlow,
                ),
                dispatch = {}
            )
        }

        composeTestRule
            .onNodeWithText("Movie1")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Movie2")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Movie3")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_displayLoadingScreenWhenLoadingStateIsRefreshAndMovieListIsEmpty() {

        val pagingDataFlow: Flow<PagingData<Movie>> = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { EmptyPagingSource() }
        ).flow

        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(
                    status = HomeState.Status.Movies,
                    movies = pagingDataFlow
                ),
                dispatch = {}
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Loading")
            .assertIsDisplayed()
    }

    @Test
    fun homeScreen_displayErrorScreenWhenLoadingStateIsRefreshAndErrorHappen() {

        val pagingDataFlow: Flow<PagingData<Movie>> = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { ErrorPagingSource() }
        ).flow

        composeTestRule.setContent {
            HomeScreen(
                state = HomeState(
                    status = HomeState.Status.Movies,
                    movies = pagingDataFlow,
                ),
                dispatch = {}
            )
        }

        composeTestRule
            .onNodeWithText("Seems like something went wrong.")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Try again")
            .assertIsDisplayed()
    }

}


private class ErrorPagingSource : PagingSource<Int, Movie>() {
    private val errorMessage = "Something went wrong!"
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return LoadResult.Error(Exception(errorMessage))
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null
}

private class EmptyPagingSource : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        delay(1000)
        return LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null,
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null
}

private class FakeMoviePagingSource : PagingSource<Int, Movie>() {
    val fakeMovies = listOf(
        Movie(
            title = "Movie1",
            overview = "overView1",
            posterPath = "/poster1.jpb",
        ),
        Movie(
            title = "Movie2",
            overview = "overView2",
            posterPath = "/poster2.jpb",
        ),
        Movie(
            title = "Movie3",
            overview = "overView3",
            posterPath = "/poster3.jpb",
        )
    )

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return LoadResult.Page(
            data = fakeMovies,
            prevKey = null,
            nextKey = null,
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? = null
}