package mozhde.shams.features.home.ui

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import mozhde.shams.features.home.domain.Movie

data class HomeState(
    val status: Status = Status.Loading,
    val movies: Flow<PagingData<Movie>> = emptyFlow(),
    val needsRetry: Boolean = false,
) {
    sealed class Status {
        data object Loading : Status()
        data object Movies : Status()
        data object Error : Status()
    }
}