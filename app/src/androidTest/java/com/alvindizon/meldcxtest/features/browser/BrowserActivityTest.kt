package com.alvindizon.meldcxtest.features.browser

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.provider.MediaStore
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import com.alvindizon.meldcxtest.R
import com.alvindizon.meldcxtest.core.Const
import com.alvindizon.meldcxtest.features.history.HistoryActivity
import kotlinx.android.synthetic.main.include_browser_toolbar.view.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class BrowserActivityTest {

    // region constants
    companion object {
        const val FILENAME = "filename.jpg"
        const val URL = "https://google.com"
    }
    // endregion constants

    // region helper fields
    @get:Rule
    val intentsTestRule = IntentsTestRule(BrowserActivity::class.java)
    // endregion helper fields


    @Before
    fun setUp() {
        val result = createActivityResult()
        intending(hasComponent(HistoryActivity::class.simpleName)).respondWith(result)
    }

    // TODO this test doesn't pass, needs fixing
    @Test
    fun browser_success_shouldOpenHistory() {
        onView(withId(R.id.capture)).perform(click())
        intended(hasPackage(HistoryActivity::class.java.name))
    }

    // TODO this test doesn't pass, needs fixing
    @Test
    fun browser_success_imageViewVisible() {
        // Arrange
        // Act
        onView(withId(R.id.capture)).perform(click())
        // Assert
        onView(withId(R.id.history_image)).check(matches(isDisplayed()))
    }

    @Test
    fun browser_initial_webViewIsVisible() {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.webview)).check(matches(isDisplayed()))
    }

    @Test
    fun browser_initial_toolbarIsVisible() {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.browser_toolbar)).check(matches(isDisplayed()))
    }

    @Test
    fun browser_initial_imageIsGone() {
        // Arrange
        // Act
        // Assert
        onView(withId(R.id.history_image)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    // region helper methods
    fun createActivityResult(): ActivityResult {
        val resultData = Intent()
        resultData.putExtra(Const.FILENAME_KEY, FILENAME)
        resultData.putExtra(Const.URL_KEY, URL)
        return ActivityResult(Activity.RESULT_OK, resultData)
    }


    // endregion helper methods

    // region helper classes

    // endregion helper classes

}