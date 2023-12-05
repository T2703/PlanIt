package UI_Testing;

import static android.os.Trace.isEnabled;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

import static org.hamcrest.CoreMatchers.not;

import android.view.View;
import profile.CreateAccountPage;


/**
 * This tests the create account page
 *
 * @author Tristan Nono
 */
@RunWith(AndroidJUnit4.class)
public class CreateAccountTest {
    @Rule
    public ActivityScenarioRule<CreateAccountPage> activityRule = new ActivityScenarioRule<>(CreateAccountPage.class);

    @Test
    public void testUIComponents() {
        // Check if the create account button is displayed
        onView(withId(R.id.create_account_button)).check(matches(isDisplayed()));

        // Check if the username EditText is displayed
        onView(withId(R.id.group_description)).check(matches(isDisplayed()));

        // Check if the email EditText is displayed
        onView(withId(R.id.input_email)).check(matches(isDisplayed()));

        // Check if the password EditText is displayed
        onView(withId(R.id.input_password)).check(matches(isDisplayed()));

        // Check if the return login button is displayed
        onView(withId(R.id.return_login_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testCreateAccountButtonInitialState() {
        // Check if the create account button is initially disabled
        onView(withId(R.id.create_account_button)).check(matches(not(isEnabled())));
    }

    @Test
    public void typeInTextFields() {
        // Type in the username EditText
        onView(withId(R.id.group_description)).perform(typeText("testUsername"), ViewActions.closeSoftKeyboard());

        // Type in the email EditText
        onView(withId(R.id.input_email)).perform(typeText("test@example.com"), ViewActions.closeSoftKeyboard());

        // Type in the password EditText
        onView(withId(R.id.input_password)).perform(typeText("testPassword"), ViewActions.closeSoftKeyboard());
    }
}
