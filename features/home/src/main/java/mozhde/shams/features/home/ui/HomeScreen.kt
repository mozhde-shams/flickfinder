package mozhde.shams.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import mozhde.shams.features.home.domain.Movie
import coil3.compose.AsyncImage

@Composable
internal fun HomeScreen(
    state: HomeState,
    dispatch: (HomeEvent) -> Unit,
) {
    when (state.status) {
        is HomeState.Status.Loading -> LoadingScreen()
        is HomeState.Status.Error -> ErrorScreen(dispatch)
        is HomeState.Status.Movies -> MoviesScreen(state)
    }
}

@Composable
private fun LoadingScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp)
        )
    }
}

@Composable
private fun MoviesScreen(
    state: HomeState
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        verticalItemSpacing = 8.dp,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 4.dp),
    ) {
        items(state.movies.size) { index ->
            MovieItem(
                movie = state.movies[index],
            )
        }
    }
}

@Composable
private fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.background(Color.White),
        ) {
            AsyncImage(
                model = movie.posterPath,
                contentDescription = null,
                modifier = Modifier.padding(8.dp),
            )
            Text(
                text = movie.title ?: "",
                color = Color.Red,
                modifier = Modifier.padding(8.dp),
            )
            Text(
                text = movie.overview ?: "",
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}