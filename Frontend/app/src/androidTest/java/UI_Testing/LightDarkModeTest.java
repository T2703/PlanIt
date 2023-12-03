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

import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.view.View;

import profile.Settings;

/**
 * This is just tests the switching of the light and dark.
 *
 * @author Tristan Nono
 */
public class LightDarkModeTest {
    @Rule
    public ActivityScenarioRule<Settings> activityRule = new ActivityScenarioRule<>(Settings.class);

    @Test
    public void changeToDark() {
        Espresso.onView(withId(R.id.theme_switch))
                .check(matches(isDisplayed()))
                .check(matches(isNotChecked())); // Assuming light mode is initially selected

        Espresso.onView(withId(R.id.theme_switch))
                .perform(ViewActions.click());

        Espresso.onView(withId(R.id.theme_switch))
                .check(matches(isChecked())); // Verify that the switch is now in the checked state (dark mode)
    }
}
