package UI_Testing;

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
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;

import android.view.View;
import profile.ProfilePage;

/**
 * This tests out the various things on the profile page.
 *
 * @author Tristan Nono
 */
@RunWith(AndroidJUnit4.class)
public class ProfilePageTest {
    @Rule
    public ActivityScenarioRule<ProfilePage> activityRule = new ActivityScenarioRule<>(ProfilePage.class);
    @Test
    public void testUIComponentsVisibility() {
        // Check if the profile image view is displayed
        onView(withId(R.id.profileImageView)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the change profile picture button is displayed
        onView(withId(R.id.changeImageButton)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the save changes button is displayed
        onView(withId(R.id.save_changes_button)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the delete account button is displayed
        onView(withId(R.id.delete_account_button)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the editName EditText is displayed
        onView(withId(R.id.editTextName)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the editEmail EditText is displayed
        onView(withId(R.id.editTextEmail)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the editPassword EditText is displayed
        onView(withId(R.id.editTextPassword)).check(ViewAssertions.matches(isDisplayed()));

        // Check if the settings button is displayed
        onView(withId(R.id.gear5)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testTypeInEditTextFields() {
        // Type in the editName EditText
        onView(withId(R.id.editTextName)).perform(ViewActions.typeText("John Doe"), ViewActions.closeSoftKeyboard());

        // Type in the editEmail EditText
        onView(withId(R.id.editTextEmail)).perform(ViewActions.typeText("john.doe@example.com"), ViewActions.closeSoftKeyboard());

        // Type in the editPass EditText
        onView(withId(R.id.editTextPassword)).perform(ViewActions.typeText("password123"), ViewActions.closeSoftKeyboard());
    }

}
