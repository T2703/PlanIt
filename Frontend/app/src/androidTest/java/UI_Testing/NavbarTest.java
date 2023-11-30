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

import homepage.HomePage;

@RunWith(AndroidJUnit4.class)
/**
 * A test to test the navbar simulating navigating through different pages on the navbar.
 *
 * @author Tristan Nono
 */
public class NavbarTest {
    @Rule
    public ActivityScenarioRule<HomePage> activityRule = new ActivityScenarioRule<>(HomePage.class);

    @Test
    public void homepageToCalendarToMessagesToProfile() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.calendar_button_nav)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.message_button)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.profile_button)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.save_changes_button)).check(matches(isDisplayed()));
    }

    @Test
    public void homepageToCalendarToHomepage() {
        // Click on the menu button to open the popup menu
        Espresso.onView(withId(R.id.calendar_button_nav)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.home_button)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.drawerLayout)).check(matches(isDisplayed()));
    }

    @Test
    public void homepageToProfile() {
        Espresso.onView(withId(R.id.profile_button)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.profileImageView)).check(matches(isDisplayed()));
    }

}
