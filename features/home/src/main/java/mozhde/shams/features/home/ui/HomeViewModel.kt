package mozhde.shams.features.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mozhde.shams.features.home.domain.GetMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    private val events = MutableSharedFlow<HomeEvent>()
    private val mutableState = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = mutableState

    init {
        fetchMovies()
        handleErrorScreenTryAgainClicked()
    }

    fun dispatch(event: HomeEvent) = viewModelScope.launch {
        events.emit(event)
    }
    private fun fetchMovies() {
        viewModelScope.launch {
            getMoviesUseCase.invoke(
                movieType = "now_playing",
                page = 1,
            ).fold(
                onSuccess = { movieList ->
                    mutableState.update {
                        it.copy(
                            status = HomeState.Status.Movies,
                            movies = movieList.movies,
                        )
                    }
                },
                onFailure = {
                    mutableState.update {
                        it.copy(
                            status = HomeState.Status.Error,
                        )
                    }
                }
            )
        }
    }

    private fun handleErrorScreenTryAgainClicked(){
        events
            .filterIsInstance<HomeEvent.ErrorScreenTryAgainClicked>()
            .onEach {
                mutableState.update {
                    it.copy(
                        status = HomeState.Status.Loading
                    )
                }
               fetchMovies()
            }
            .launchIn(viewModelScope)
    }
}