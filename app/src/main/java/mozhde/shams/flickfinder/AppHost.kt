package mozhde.shams.flickfinder

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import mozhde.shams.features.home.ui.HOME_GRAPH_ROUTE
import mozhde.shams.features.home.ui.homeGraph

@Composable
fun AppHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = HOME_GRAPH_ROUTE) {
        homeGraph()
    }
}