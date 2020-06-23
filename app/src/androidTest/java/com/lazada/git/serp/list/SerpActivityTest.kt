package com.lazada.git.serp.list

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.lazada.git.GitInstrumentationTest
import com.lazada.git.R
import com.lazada.git.serp.view.SerpActivity
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class SerpActivityTest : GitInstrumentationTest() {
    @Rule
    @JvmField
    var testRule = ActivityTestRule<SerpActivity>(SerpActivity::class.java)

    @Test
    fun testLoadingScreen() {
        // Test loading screen visible
        onView(withId(R.id.screenLoading))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun testRepositoryCardContent() {
        SystemClock.sleep(1000)
        // Test repo card content text
        onView(allOf(withId(R.id.cardViewTitle), first(withId(R.id.cardViewTitle))))
            .check(matches(withText(containsString("REPO NAME"))))
        onView(allOf(withId(R.id.cardViewDescription), first(withId(R.id.cardViewDescription))))
            .check(matches(withText(containsString("REPO DESC"))))
    }

    @Test
    fun testPaginationAndNextPageRetry() {
        // Test Pagination until Snackbar appears
        for (i in 0..50) {
            onView(withId(R.id.recyclerView)).perform(swipeUp())

            try {
                onView(withId(com.google.android.material.R.id.snackbar_action)).perform(click())
                // Loading view should not be displayed
                onView(withId(R.id.screenLoading)).check(matches(withEffectiveVisibility(Visibility.GONE)))
            } catch (e: Exception) {
                // Snackbar not visible, continue swiping
            }
        }
    }

    @Test
    fun testSwipeRefresh() {
        // Swipe up to top
        onView(withId(R.id.recyclerView))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))

        // Swipe to refresh
        onView(withId(R.id.recyclerView)).perform(swipeDown())

        // Test loading screen visible
        onView(withId(R.id.screenLoading))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    private fun <T> first(matcher: Matcher<T>): Matcher<T>? {
        return object : BaseMatcher<T>() {
            var isFirst = true
            override fun matches(item: Any): Boolean {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false
                    return true
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("should return first matching item")
            }
        }
    }
}
