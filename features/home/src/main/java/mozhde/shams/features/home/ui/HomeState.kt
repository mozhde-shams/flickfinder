package mozhde.shams.features.home.ui

import mozhde.shams.features.home.domain.Movie

data class HomeState(
    val status: Status = Status.Loading,
    val movies: List<Movie> = emptyList(),
) {
    sealed class Status {
        data object Loading : Status()
        data object Movies : Status()
        data object Error : Status()
    }
}