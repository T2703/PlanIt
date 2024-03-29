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

import calendar.CalendarMonthlyPage;

@RunWith(AndroidJUnit4.class)
/**
 * This is an Espresso test by testing out the navigation of the calendars.
 *
 * @author Tristan Nono
 */
public class CalendarMonthlyPageTest {
    @Rule
    public ActivityScenarioRule<CalendarMonthlyPage> activityRule = new ActivityScenarioRule<>(CalendarMonthlyPage.class);

    @Test
    public void navigateToWeeklyView() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Weekly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Weekly")).perform(ViewActions.click());

        // Checks if the this is displayed.
        Espresso.onView(withId(R.id.sunDate)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToWeeklyViewToMonthly() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Weekly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Weekly")).perform(ViewActions.click());

        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Monthly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Monthly")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.menu_calendar_button)).check(matches(isDisplayed()));

    }

    @Test
    public void navigateToWeeklyViewToMonthlyToDailyToWeekly() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Weekly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Weekly")).perform(ViewActions.click());

        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Monthly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Monthly")).perform(ViewActions.click());

        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Daily View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Daily")).perform(ViewActions.click());

        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Weekly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Weekly")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.sunDate)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToEventView() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Weekly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Events")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.filter_menu)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToDailyToEventView() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Daily View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Daily")).perform(ViewActions.click());

        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Weekly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Events")).perform(ViewActions.click());

        Espresso.onView(withId(R.id.filter_menu)).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToAnalyze() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.menu_calendar_button)).perform(ViewActions.click());

        // Click on the "Weekly View" option in the popup menu
        Espresso.onView(ViewMatchers.withText("Analyze Schedule")).perform(ViewActions.click());


    }
}
