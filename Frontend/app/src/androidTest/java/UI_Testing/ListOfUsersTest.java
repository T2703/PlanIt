package UI_Testing;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.util.Log;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.myapplication.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import followers.ListOfUsers;

@RunWith(AndroidJUnit4.class)
public class ListOfUsersTest {
    @Rule
    public ActivityScenarioRule<ListOfUsers> activityRule = new ActivityScenarioRule<>(ListOfUsers.class);

    @Test
    public void testUIComponents() {
        // Check if the back button is displayed
        onView(withId(R.id.back_button)).check(matches(isDisplayed()));

        // Check if the RecyclerView is displayed
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()));

        // Check if the toolbar is displayed
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearchBar() {
        // Perform a click on the search icon
        Espresso.onView(withId(R.id.searchBar)).perform(click());

        // Type text into the search view
        String searchText = "Test";
        Espresso.onView(isAssignableFrom(android.widget.EditText.class))
                .perform(typeText(searchText), pressKey(android.view.KeyEvent.KEYCODE_ENTER));

        Espresso.onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
    }


}
