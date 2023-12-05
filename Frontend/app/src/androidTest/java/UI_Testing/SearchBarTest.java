package UI_Testing;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import events.EventsListViewer;

/**
 * This tests out to see if the search bar works. Basically just typing things into the search bar.
 *
 * @author Tristan Nono
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class SearchBarTest {
    @Rule
    public ActivityScenarioRule<EventsListViewer> activityRule = new ActivityScenarioRule<>(EventsListViewer.class);


    @Test
    public void testSearchBar() {
        // Perform a click on the search icon
        Espresso.onView(withId(R.id.searchBar)).perform(click());

        // Type text into the search view
        String searchText = "Event Name";
        Espresso.onView(isAssignableFrom(android.widget.EditText.class))
                .perform(typeText(searchText), pressKey(android.view.KeyEvent.KEYCODE_ENTER));

        Espresso.onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearchBarThenFilterPublic() {
        // Perform a click on the search icon
        Espresso.onView(withId(R.id.searchBar)).perform(click());

        // Type text into the search view
        String searchText = "Event Name";
        Espresso.onView(isAssignableFrom(android.widget.EditText.class))
                .perform(typeText(searchText), closeSoftKeyboard());

        Espresso.onView(withId(R.id.filter_menu)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.checkbox_public)).check(matches(isDisplayed()));

    }
}
