package UI_Testing;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.view.View;

import events.EventsListViewer;

@RunWith(AndroidJUnit4.class)
/**
 * This tests out the filter menu on the event list viewer page.
 */
public class FilterMenuTest {
    @Rule
    public ActivityScenarioRule<EventsListViewer> activityRule = new ActivityScenarioRule<>(EventsListViewer.class);

    @Test
    public void filterPrivateEvents() {
        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_private)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_private)).check(matches(isDisplayed()));
    }

    @Test
    public void filterGroupEvents() {
        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_group)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_group)).check(matches(isDisplayed()));
    }

    @Test
    public void filterPublicEvents() {
        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).check(matches(isDisplayed()));
    }

    @Test
    public void filterPrivateEventsUnFilter() {
        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_private)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_private)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_private)).check(matches(isDisplayed()));
    }

    @Test
    public void filterGroupEventsUnFilter() {
        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_group)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_group)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_group)).check(matches(isDisplayed()));
    }

    @Test
    public void filterPublicEventsUnFilter() {
        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).check(matches(isDisplayed()));
    }

    @Test
    public void filterPrivateEventsPublicEvents() {
        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_private)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).check(matches(isDisplayed()));
    }

    @Test
    public void filterPrivateEventsPublicEventsGroupEventsUnfilterAll() {
        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_private)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_group)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_private)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_group)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_group)).check(matches(isDisplayed()));
    }

    @Test
    public void filterAll() {
        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_private)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_group)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).check(matches(isDisplayed()));
    }


}
