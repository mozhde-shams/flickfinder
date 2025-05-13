package mozhde.shams.features.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import mozhde.shams.features.home.ui.ErrorItem
import mozhde.shams.features.home.ui.ErrorScreen
import mozhde.shams.features.home.ui.HomeEvent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun errorScreen_displaysErrorMessageAndRetryButton() {
        var retryClicked = false
        val dispatch: (HomeEvent) -> Unit = {
            retryClicked = true
        }
        composeTestRule.setContent {
            ErrorScreen(
                dispatch = dispatch,
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Seems like something went wrong.")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Seems like something went wrong.")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Try again")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Try again")
            .performClick()

        assert(retryClicked)
    }

    @Test
    fun errorItem_displayRetryButton() {
        var retryClicked = false
        val dispatch: (HomeEvent) -> Unit = {
            retryClicked = true
        }
        composeTestRule.setContent {
            ErrorItem(
                dispatch = dispatch,
            )
        }

        composeTestRule
            .onNodeWithText("Try again")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Try again")
            .performClick()

        assert(retryClicked)
    }
}