package kade.submission2.footballmatchschedule.view

import android.view.KeyEvent
import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import kade.submission2.footballmatchschedule.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingresource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingresource)
    }


    @Test
    fun testBottomNavigationView() {

        onView(withId(R.id.menu_pencarian)).perform(click())
        onView(withId(R.id.iconSearchView)).check(matches(isDisplayed()))
        onView(withId(R.id.iconSearchView)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(
            typeText("Juventus"),
            ViewActions.pressKey(KeyEvent.KEYCODE_ENTER)
        )
        EspressoIdlingResource.increment()
        onData(withId(R.id.recyclerPencaraian)).check(matches(isDisplayed()))

        if (!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }

        onView(withId(R.id.iconSearchView)).check(matches(isDisplayed()))
        onView(withId(R.id.iconSearchView)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(
            typeText("chelsea"),
            ViewActions.pressKey(KeyEvent.KEYCODE_ENTER)
        )

        EspressoIdlingResource.increment()
        onView(withId(R.id.recyclerPencaraian)).check(matches(isDisplayed()))

        if (!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }

        EspressoIdlingResource.increment()
        onView(withId(R.id.recyclerPencaraian)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        if (!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        Espresso.pressBack()
    }

    object EspressoIdlingResource {
        private const val RESOURCE = "GLOBAL"
        private val countingIdlingResource = CountingIdlingResource(RESOURCE)

        val idlingresource: IdlingResource
            get() = countingIdlingResource

        fun increment() {
            countingIdlingResource.increment()
        }

        fun decrement() {
            countingIdlingResource.decrement()
        }
    }
}