package mozhde.shams.features.home.ui

sealed class HomeEvent {
    data object ErrorScreenTryAgainClicked: HomeEvent()
}