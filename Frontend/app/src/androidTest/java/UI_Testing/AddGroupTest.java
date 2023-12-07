package UI_Testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.not;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
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

@RunWith(AndroidJUnit4.class)
public class AddGroupTest {
    @Rule
    public ActivityScenarioRule<AddGroup> activityScenarioRule = new ActivityScenarioRule<>(AddGroup.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void testUIComponents() {
        onView(withId(R.id.group_name)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.group_description)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.group_title_page)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.create_group_button)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.add_group_back_button)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testCreateGroupButtonEnabled() {
        onView(withId(R.id.group_name)).perform(typeText("Test Group"));
        onView(withId(R.id.group_description)).perform(typeText("Group Description"));

        onView(withId(R.id.create_group_button)).check(matches(isEnabled()));
    }

    @Test
    public void testCreateGroupButtonDisabled() {
        onView(withId(R.id.create_group_button)).check(matches(not(isEnabled())));
    }

    @Test
    public void testCreateGroup() {
        onView(withId(R.id.group_name)).perform(typeText("Test Group"));
        onView(withId(R.id.group_description)).perform(typeText("Group Description"));

        onView(withId(R.id.create_group_button)).perform(click());

        intended(hasComponent(MemberViewer.class.getName()));
    }

    @Test
    public void testBackButton() {
        onView(withId(R.id.add_group_back_button)).check(matches(isCompletelyDisplayed())).perform(click());

        intended(hasComponent(MemberViewer.class.getName()));
    }

    @After
    public void cleanUp() {
        Intents.release();
    }
}
