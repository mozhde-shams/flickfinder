package mozhde.shams.features.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import mozhde.shams.features.home.domain.Movie
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.Flow
import mozhde.shams.features.home.R

@Composable
internal fun HomeScreen(
    state: HomeState,
    dispatch: (HomeEvent) -> Unit,
) {
    when (state.status) {
        is HomeState.Status.Loading -> LoadingScreen()
        is HomeState.Status.Error -> ErrorScreen(dispatch)
        is HomeState.Status.Movies -> MoviesScreen(
            movies = state.movies,
            dispatch = dispatch,
            needToRefresh = state.needsRetry,
        )
    }
}

@Composable
private fun LoadingScreen() {
    val loadingString = stringResource(id = R.string.loading)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(64.dp)
                .semantics {
                    contentDescription = loadingString
                },
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp,
        )
    }
}

@Composable
private fun LoadingItem(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 4.dp,
        )
    }
}

@Composable
private fun MoviesScreen(
    movies: Flow<PagingData<Movie>>,
    dispatch: (HomeEvent) -> Unit,
    needToRefresh: Boolean,
) {
    val lazyPagingItems: LazyPagingItems<Movie> = movies.collectAsLazyPagingItems()

    if (needToRefresh) {
        lazyPagingItems.retry()
    }

    when {
        lazyPagingItems.loadState.refresh is LoadState.Loading && lazyPagingItems.itemCount == 0 ->
            LoadingScreen()

        lazyPagingItems.loadState.refresh is LoadState.Error && lazyPagingItems.itemCount == 0 ->
            ErrorScreen(dispatch)

        else -> {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(all = 4.dp),
            ) {
                items(lazyPagingItems.itemCount) { index ->
                    lazyPagingItems[index]?.let { movie ->
                        MovieItem(movie)
                    }
                }
                lazyPagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { LoadingItem() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingItem() }
                        }

                        loadState.refresh is LoadState.Error -> {
                            item { ErrorItem(dispatch) }
                        }

                        loadState.append is LoadState.Error -> {
                            item { ErrorItem(dispatch) }
                        }
                    }
                }
            }
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