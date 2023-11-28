package espresso;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.assertion.ViewAssertions;
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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {
    @Rule
    public ActivityScenarioRule<HomePage> activityRule = new ActivityScenarioRule<HomePage>(HomePage.class);

    @Test
    public void numberOfNotificationsIsDisplayed() {
        onView(ViewMatchers.withId(R.id.notificationCount)).check(ViewAssertions.matches(ViewMatchers.withText(Matchers.containsString("0"))));
    }
}
