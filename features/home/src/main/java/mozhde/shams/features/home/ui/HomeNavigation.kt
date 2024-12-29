package mozhde.shams.features.home.ui

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HOME_ROUTE = "home"

internal fun NavGraphBuilder.homeScreen() {
    composable(route = HOME_ROUTE) {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.state.collectAsState()
        HomeScreen(
            state= state,
            dispatch = viewModel::dispatch
            )
    }
}