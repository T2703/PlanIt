package UI_Testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import followers.FollowersPage;
import followers.FollowingPage;

@RunWith(AndroidJUnit4.class)
public class FollowingPageTest {
    @Rule
    public ActivityScenarioRule<FollowingPage> activityRule = new ActivityScenarioRule<>(FollowingPage.class);

    @Test
    public void testUIComponents() {
        // Check if the back button is displayed
        onView(withId(R.id.back_button)).check(matches(isDisplayed()));

        // Check if the RecyclerView is displayed
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

        // Check if the toolbar is displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

}
