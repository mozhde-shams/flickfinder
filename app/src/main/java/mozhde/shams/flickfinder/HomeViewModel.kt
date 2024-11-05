package mozhde.shams.flickfinder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mozhde.shams.features.home.domain.GetMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    fun fetchMovies(){
        viewModelScope.launch {
            getMoviesUseCase.invoke(
                movieType = "now_playing",
                page = 1,
            )
        }
    }
}