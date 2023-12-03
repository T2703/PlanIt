package UI_Testing;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;

import android.view.View;

import homepage.HomePage;

@RunWith(AndroidJUnit4.class)
/**
 * A test to test the navbar simulating navigating through different pages on the navbar.
 *
 * @author Joshua Gutierrez
 */
public class NavbarTest {
    @Rule
    public ActivityScenarioRule<HomePage> activityRule = new ActivityScenarioRule<>(HomePage.class);

    @Test
    public void navigateToHomePage() {
        onView(withId(R.id.home_button)).perform(click());
        onView(withId(R.id.title)).check(matches(withText("Home")));
    }

    @Test
    public void navigateToCalendarPage() {
        onView(withId(R.id.calendar_button_nav)).perform(click());
        onView((withId(R.id.menu_calendar_button))).check(matches(isDisplayed()));
    }

    @Test
    public void navigateToTeamPage() {
        onView(withId(R.id.message_button)).perform(click());
        
    }

    @Test
    public void navigateToProfilePage() {
        onView(withId(R.id.profile_button)).perform(click());

    }

    public void navigateBackToHomepage() {
        onView(withId(R.id.calendar_button_nav)).perform(click());
        onView(withId(R.id.home_button)).perform(click());
    }
}
