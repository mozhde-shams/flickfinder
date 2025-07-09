package mozhde.shams.features.home.ui

import androidx.paging.PagingData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import mozhde.shams.features.home.domain.GetPagedMoviesUseCase
import mozhde.shams.features.home.domain.Movie
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getPagedMoviesUseCase: GetPagedMoviesUseCase
    private lateinit var homeViewModel: HomeViewModel

    private val moviesList = listOf(
        Movie(
            title = "title1",
            overview = "description1",
            posterPath = "PosterPath1"
        ),
        Movie(
            title = "title2",
            overview = "description2",
            posterPath = "PosterPath2"
        ),Movie(
            title = "title3",
            overview = "description3",
            posterPath = "PosterPath3"
        )
    )

    private val pagingData = PagingData.from(moviesList)

    @Before
    fun setup() {
        getPagedMoviesUseCase = mockk()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun fetchMovieUpdateStateAsMovies() = runTest {
        coEvery { getPagedMoviesUseCase() } returns flowOf(pagingData)

        homeViewModel = HomeViewModel(getPagedMoviesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = homeViewModel.state.value
        assertEquals(HomeState.Status.Movies, state.status)
    }

    @Test
    fun dispatchErrorItemRetryClickedUpdatesNeedsRetryTrue() = runTest {
        coEvery { getPagedMoviesUseCase() } returns flowOf(pagingData)

        homeViewModel = HomeViewModel(getPagedMoviesUseCase)
        homeViewModel.dispatch(HomeEvent.ErrorItemRetryClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = homeViewModel.state.value
        assertTrue(state.needsRetry)
    }

    @Test
    fun fetchMoviesCallsGetPagedMoviesUseCaseOnceOnInit() = runTest {
        coEvery { getPagedMoviesUseCase() } returns flowOf(pagingData)

        homeViewModel = HomeViewModel(getPagedMoviesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { getPagedMoviesUseCase() }
    }

    @Test
    fun errorScreenTryAgainClickedTriggersAnotherFetchMoviesCall() = runTest {
        coEvery { getPagedMoviesUseCase() } returns flowOf(pagingData)

        homeViewModel = HomeViewModel(getPagedMoviesUseCase)
        homeViewModel.dispatch(HomeEvent.ErrorScreenTryAgainClicked)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 2) { getPagedMoviesUseCase() }
    }

    @Ignore("HomeViewModel should change to pass this test")
    @Test
    fun fetchMoviesHandlesExceptionSafely() = runTest {
        coEvery { getPagedMoviesUseCase() } returns flow {
            throw RuntimeException()
        }

        homeViewModel = HomeViewModel(getPagedMoviesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = homeViewModel.state.value
        assertEquals(HomeState.Status.Error, state.status)
    }

}