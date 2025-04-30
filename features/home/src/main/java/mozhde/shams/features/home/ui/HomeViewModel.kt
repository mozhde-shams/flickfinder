package mozhde.shams.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mozhde.shams.features.home.domain.GetPagedMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPagedMoviesUseCase: GetPagedMoviesUseCase,
) : ViewModel() {

    private val events = MutableSharedFlow<HomeEvent>()
    private val mutableState = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = mutableState

    init {
        fetchMovies()
        handleErrorScreenTryAgainClicked()
        handleErrorItemRetryClicked()
    }

    fun dispatch(event: HomeEvent) = viewModelScope.launch {
        events.emit(event)
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            val movies = getPagedMoviesUseCase()
                .cachedIn(viewModelScope)

            mutableState.update {
                it.copy(
                    status = HomeState.Status.Movies,
                    movies = movies,
                )
            }
        }
    }

    private fun handleErrorScreenTryAgainClicked() {
        events
            .filterIsInstance<HomeEvent.ErrorScreenTryAgainClicked>()
            .onEach {
                fetchMovies()
            }
            .launchIn(viewModelScope)
    }

    private fun handleErrorItemRetryClicked(){
        events
            .filterIsInstance<HomeEvent.ErrorItemRetryClicked>()
            .onEach {
                mutableState.update {
                    it.copy(
                        needsRetry = true,
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}