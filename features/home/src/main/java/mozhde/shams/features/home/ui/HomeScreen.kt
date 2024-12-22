package mozhde.shams.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Text
import mozhde.shams.features.home.R

@Composable
internal fun HomeScreen() {
    Text(text = stringResource(id = R.string.home))
}