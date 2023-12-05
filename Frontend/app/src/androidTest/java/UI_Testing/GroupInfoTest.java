package UI_Testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import groups.EditGroup;
import groups.GroupInfo;
import groups.MemberViewer;
import messages.MessageView;

/**
 * @author Joshua Gutierrez
 */
@RunWith(AndroidJUnit4.class)
public class GroupInfoTest {
    @Rule
    public ActivityScenarioRule<GroupInfo> activityRule = new ActivityScenarioRule<>(GroupInfo.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void testUIElements() {
        onView(withId(R.id.group_name_two)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.group_desc)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.back_button_two)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        onView(withId(R.id.menu_button_two)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void testBackButton() {
        onView(withId(R.id.back_button_two)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MemberViewer.class.getName()));
    }

    @Test
    public void testMenuButton() {
        onView(withId(R.id.menu_button_two)).perform(click());

        onView(withText("To Chats")).check(matches(isDisplayed()));
        onView(withText("Edit Group")).check(matches(isDisplayed()));
        onView(withText("Delete Group")).check(matches(isDisplayed()));
    }

    @Test
    public void testToChatOption() {
        onView(withId(R.id.menu_button_two)).perform(click());
        onView(withText("To Chats")).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MessageView.class.getName()));
    }

    @Test
    public void testToEditOption() {
        onView(withId(R.id.menu_button_two)).perform(click());
        onView(withText("Edit Group")).perform(click());

        Intents.intended(IntentMatchers.hasComponent(EditGroup.class.getName()));
    }

    @Test
    public void testToDeleteGroupOption() {
        onView(withId(R.id.menu_button_two)).perform(click());

        // Click the delete option
        onView(withText("Delete Group")).perform(click());

        Intents.intended(IntentMatchers.hasComponent(MemberViewer.class.getName()));
    }

    @After
    public void cleanUp() {
        Intents.release();
    }
}
