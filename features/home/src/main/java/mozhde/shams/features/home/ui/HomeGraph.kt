package mozhde.shams.features.home.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation

const val HOME_GRAPH_ROUTE = "home_graph"


fun NavGraphBuilder.homeGraph() {
    navigation(
        startDestination = HOME_ROUTE,
        route = HOME_GRAPH_ROUTE,
    ) {
        homeScreen()
    }
}