package UI_Testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.myapplication.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import groups.EditGroup;

@RunWith(AndroidJUnit4.class)
public class EditGroupTest {
    @Rule
    public ActivityScenarioRule<EditGroup> activityRule = new ActivityScenarioRule<>(EditGroup.class);

    @Test
    public void testEditGroupUI() {
        onView(withId(R.id.group_name)).perform(typeText("New Group Name"), closeSoftKeyboard());
        onView(withId(R.id.group_description)).perform(typeText("New Group Description"), closeSoftKeyboard());

        onView(withId(R.id.group_name)).check(matches(withText("New Group Name")));
        onView(withId(R.id.group_description)).check(matches(withText("New Group Description")));

        onView(withId(R.id.edit_button)).perform(click());

        onView(withId(R.id.group_name)).check(matches(withText("New Group Name")));
        onView(withId(R.id.group_description)).check(matches(withText("New Group Description")));
    }
}
