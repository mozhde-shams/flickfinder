package mozhde.shams.features.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mozhde.shams.features.home.R

@Composable
fun ErrorScreen(
    dispatch: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.something_went_wrong),
            contentDescription = stringResource(id = R.string.something_went_wrong)
        )
        Text(text = stringResource(id = R.string.something_went_wrong))
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                dispatch(HomeEvent.ErrorScreenTryAgainClicked)
            },
        ) {
            Text(text = stringResource(id = R.string.try_again))
        }
    }
}

@Composable
fun ErrorItem(
    dispatch: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = {
                dispatch(HomeEvent.ErrorItemRetryClicked)
            },
        ) {
            Text(text = stringResource(id = R.string.try_again))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        dispatch = {}
    )
}