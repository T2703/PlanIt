package UI_Testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import groups.AddGroup;
import groups.MemberViewer;

/**
 * @author Joshua Gutierrez
 */
@RunWith(AndroidJUnit4.class)
public class MemberViewerTest {
    @Rule
    public ActivityScenarioRule<MemberViewer> activityScenarioRule = new ActivityScenarioRule<>(MemberViewer.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void testRecyclerViewIsDisplayed() {
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateGroupButtonIsClickable() {
        onView(withId(R.id.create_button_add)).perform(click());
        intended(hasComponent(AddGroup.class.getName()));
    }

    @Test
    public void testGetGroupsRequest() {
        onView(withId(R.id.recycler_view)).check(matches((hasChildCount(0))));
    }

    @After
    public void cleanUp() {
        Intents.release();
    }
}
