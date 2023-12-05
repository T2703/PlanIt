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

import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

import android.view.View;

import calendar.CalendarDailyPage;

/**
 * Tests the calendar daily page
 *
 * @author Tristan Nono
 */
@RunWith(AndroidJUnit4.class)
public class CalendarDailyPageTest {
    @Rule
    public ActivityScenarioRule<CalendarDailyPage> activityRule = new ActivityScenarioRule<>(CalendarDailyPage.class);

    @Test
    public void testUiComponents() {
        // Check if the day buttons are displayed
        Espresso.onView(ViewMatchers.withId(R.id.nextDayButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.prevDayButton)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check if the day of the month TextView is displayed
        Espresso.onView(ViewMatchers.withId(R.id.dayOfMonthText)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check if the RecyclerView is displayed
        Espresso.onView(ViewMatchers.withId(R.id.liste)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Check if the menu button is displayed
        Espresso.onView(ViewMatchers.withId(R.id.menu_calendar_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void navigateTheCalendars() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Weekly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Weekly")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.sunDate)).check(matches(isDisplayed()));

        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("Monthly")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.menu_calendar_button)).check(matches(isDisplayed()));

        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        Espresso.onView(ViewMatchers.withText("Daily")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.dayOfMonthText)).check(matches(isDisplayed()));
    }

    @Test
    public void clickArrows() {
        Espresso.onView(withId(R.id.nextDayButton)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.prevDayButton)).perform(ViewActions.click());
    }
}
