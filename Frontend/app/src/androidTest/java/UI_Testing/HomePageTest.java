package UI_Testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.myapplication.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import homepage.HomePage;

/**
 * @author Joshua Gutierrez
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomePageTest {
    @Rule
    public ActivityScenarioRule<HomePage> activityRule = new ActivityScenarioRule<HomePage>(HomePage.class);

    @Test
    public void numberOfNotificationsIsDisplayed() {
        onView(withId(R.id.notificationCount)).check(matches(ViewMatchers.withText(Matchers.containsString("0"))));
    }

    @Test
    public void navigateToNotificationsPage() {
        onView(withId(R.id.menuButton)).check(matches(isDisplayed()));
        onView(withId(R.id.title)).check(matches(withText("Home")));
        onView(withId(R.id.notificationIcon)).check(matches(isDisplayed()));

        onView(withId(R.id.notificationIcon)).perform(click());
        onView(withId(R.id.notificationsPageHeader)).check(matches(isDisplayed()));
    }

    @Test
    public void openNavigation() {
        onView(withId(R.id.menuButton)).perform(click());
        onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));
    }
}