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
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import messages.MessageView;
@RunWith(AndroidJUnit4.class)
public class MessageViewTest {
    @Rule
    public ActivityScenarioRule<MessageView> activityRule = new ActivityScenarioRule<>(MessageView.class);

    @Test
    public void testUIComponents() {
        // Check if UI components are displayed
        Espresso.onView(withId(R.id.send_button)).check(ViewAssertions.matches(isDisplayed()));
        Espresso.onView(withId(R.id.user_message)).check(ViewAssertions.matches(isDisplayed()));
        Espresso.onView(withId(R.id.username)).check(ViewAssertions.matches(isDisplayed()));
        Espresso.onView(withId(R.id.message_tv)).check(ViewAssertions.matches(isDisplayed()));

        // Perform some actions on UI components
        Espresso.onView(withId(R.id.user_message)).perform(ViewActions.typeText("Hello, this is a test message"));

        // Check if the message appears on the screen after sending
        Espresso.onView(withId(R.id.user_message))
                .check(ViewAssertions.matches(withText("Hello, this is a test message")));
    }

    @Test
    public void testButtons() {
        // Check if UI components are displayed
        Espresso.onView(withId(R.id.send_button)).check(ViewAssertions.matches(isDisplayed()));
        Espresso.onView(withId(R.id.send_button)).perform(ViewActions.click());
    }

}
