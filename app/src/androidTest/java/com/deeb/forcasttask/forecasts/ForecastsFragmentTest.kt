package com.deeb.forcasttask.forecasts

import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deeb.forcasttask.R
import com.deeb.forcasttask.views.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ForecastsFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun check_all_page_items_displayed() {
        onView(withId(R.id.st_search))
            .check(matches(isDisplayed()))

        onView(withId(R.id.res_forecasts))
            .check(matches(isDisplayed()))

        onView(withId(R.id.bt_retry))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.tx_error))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.load))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

    }
    @Test
    fun is_searchView_enabled_onAppLaunch() {
        onView(withId(R.id.st_search))
            .check(matches(isEnabled()))
    }


    @Test
    fun submitSearch_then_start_load() {
        onView(withId(R.id.load))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))

       onView(isAssignableFrom(EditText::class.java))
           .perform(
            typeText("cairo"),
            pressImeActionButton()
        )
        onView(withId(R.id.load))
            .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }


}