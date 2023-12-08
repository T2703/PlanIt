package UI_Testing;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

import android.view.View;

import calendar.CalendarWeeklyPage;

/**
 * Tests out the calendar weekly view.
 *
 * @author Tristan Nono
 */
@RunWith(AndroidJUnit4.class)
public class CalendarWeeklyTest {
    @Rule
    public ActivityScenarioRule<CalendarWeeklyPage> activityRule = new ActivityScenarioRule<>(CalendarWeeklyPage.class);

    @Test
    public void testUIComponents() {
        // Check if the days of the week TextViews are displayed
        //onView(withId(R.id.sunDate)).check(matches(isDisplayed()));
        onView(withId(R.id.monDate)).check(matches(isDisplayed()));
        onView(withId(R.id.tueDate)).check(matches(isDisplayed()));
        onView(withId(R.id.wedDate)).check(matches(isDisplayed()));
        onView(withId(R.id.thuDate)).check(matches(isDisplayed()));
        onView(withId(R.id.friDate)).check(matches(isDisplayed()));
        onView(withId(R.id.satDate)).check(matches(isDisplayed()));

        // Check if the "Next" and "Previous" week buttons are displayed
        onView(withId(R.id.nextWeekButton)).check(matches(isDisplayed()));
        onView(withId(R.id.prevWeekButton)).check(matches(isDisplayed()));

        // Check if the RecyclerView is displayed
        onView(withId(R.id.event_item_list_view)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateTheCalendars() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("Monthly")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.menu_calendar_button)).check(matches(isDisplayed()));

        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("Weekly")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.menu_calendar_button)).check(matches(isDisplayed()));

        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("Daily")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.dayOfMonthText)).check(matches(isDisplayed()));
    }

    @Test
    public void clickArrows() {
        Espresso.onView(withId(R.id.nextWeekButton)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.prevWeekButton)).perform(ViewActions.click());
    }
    @Test
    public void clickDays() {
        Espresso.onView(withId(R.id.sunDate)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.monDate)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.tueDate)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.wedDate)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.thuDate)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.friDate)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.satDate)).perform(ViewActions.click());

    }

    @Test
    public void navigateToAnalyze() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Weekly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Analyze Schedule")).perform(ViewActions.click());


    }

}
