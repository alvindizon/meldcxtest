package com.alvindizon.meldcxtest.features.history

import android.app.Activity
import android.view.View
import android.view.ViewConfiguration
import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.ActivityResultMatchers.hasResultCode
import androidx.test.espresso.contrib.ActivityResultMatchers.hasResultData
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alvindizon.meldcxtest.R
import com.alvindizon.meldcxtest.core.Const
import org.hamcrest.Matcher
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


// Some notes before running this test(taken from https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-testing-test-doubles/#9)
// On your testing device, go to Settings > Developer options.
// Disable these three settings: Window animation scale, Transition animation scale, and Animator duration scale.
// Also, the DB needs at two records in the DB, for history_success_correctCodeAndKeysOnItemClick and history_success_itemDeletedOnDeleteButtonPress to pass
@RunWith(MockitoJUnitRunner::class)
class HistoryActivityTest {

    // region constants
    companion object {
        const val indexToBeRemoved = 0
        var beforeUrl = "https://google.com"
    }
    // endregion constants

    // region helper fields
    @get:Rule
    val intentsTestRule = IntentsTestRule(HistoryActivity::class.java)
    // endregion helper fields

    @Test
    fun history_initial_toolbarIsVisible() {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.history_toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun history_initial_progressBarIsGone() {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.progress_bar))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun history_initial_rvContainerIsVisible() {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.container)).check(matches(isDisplayed()))
    }

    @Test
    fun history_success_correctCodeAndKeysOnItemClick() {
        // Arrange
        // Act
        onView(withId(R.id.rv_history)).perform(RecyclerViewActions.actionOnItemAtPosition<HistoryAdapter.ViewHolder>(indexToBeRemoved,
            object : ViewAction {
                override fun getDescription(): String = "press item"

                override fun getConstraints(): Matcher<View> = isDisplayed()

                override fun perform(uiController: UiController, view: View) {
                    view.findViewById<View>(R.id.item_layout).performClick()
                    uiController.loopMainThreadForAtLeast(ViewConfiguration.getTapTimeout().toLong())
                }
            }))
        // Assert
        assertThat(intentsTestRule.activityResult, hasResultCode(Activity.RESULT_OK))
        assertThat(intentsTestRule.activityResult, hasResultData(hasExtraWithKey(Const.FILENAME_KEY)))
        assertThat(intentsTestRule.activityResult, hasResultData(hasExtraWithKey(Const.URL_KEY)))
    }

    @Test
    fun history_success_itemDeletedOnDeleteButtonPress() {
        // Arrange
        onView(withId(R.id.rv_history))
            .perform(RecyclerViewActions.actionOnItemAtPosition<HistoryAdapter.ViewHolder>(indexToBeRemoved,
                object : ViewAction {
                    override fun getDescription(): String = "get before url"

                    override fun getConstraints(): Matcher<View> = isDisplayed()

                    override fun perform(uiController: UiController, view: View) {
                        beforeUrl = view.findViewById<TextView>(R.id.url).text.toString()
                    }
                }))

        // Act
        onView(withId(R.id.rv_history))
            .perform(RecyclerViewActions.actionOnItemAtPosition<HistoryAdapter.ViewHolder>(indexToBeRemoved,
                object : ViewAction {
                    override fun getDescription(): String = "press delete"

                    override fun getConstraints(): Matcher<View> = isDisplayed()

                    override fun perform(uiController: UiController, view: View) {
                        view.findViewById<View>(R.id.bt_delete).performClick()
                        uiController.loopMainThreadForAtLeast(ViewConfiguration.getTapTimeout().toLong())
                    }
                }))

        // Assert
        onView(withId(R.id.rv_history))
            .perform(RecyclerViewActions.actionOnItemAtPosition<HistoryAdapter.ViewHolder>(indexToBeRemoved,
                object : ViewAction {
                    override fun getDescription(): String = "check if item is removed"

                    override fun getConstraints(): Matcher<View> = isDisplayed()

                    override fun perform(uiController: UiController, view: View) {
                        val content = view.findViewById<TextView>(R.id.url).text.toString()
                        Assert.assertFalse(content == beforeUrl)
                    }
                }))
    }


    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes

}