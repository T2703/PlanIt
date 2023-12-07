package UI_Testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.myapplication.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import events.CreateEventPage;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateEventPageTest {
    @Rule
    public ActivityScenarioRule<CreateEventPage> activityRule = new ActivityScenarioRule<>(CreateEventPage.class);

    @Test
    public void testUIComponents() {
        onView(withId(R.id.event_type_dropdown)).check(matches(isDisplayed()));
        onView(withId(R.id.event_name_text)).check(matches(isDisplayed()));

        onView(withId(R.id.start_date_input)).check(matches(isDisplayed()));
        onView(withId(R.id.end_date_input)).check(matches(isDisplayed()));
        onView(withId(R.id.start_time_input)).check(matches(isDisplayed()));
        onView(withId(R.id.end_time_input)).check(matches(isDisplayed()));

        onView(withId(R.id.event_location_input)).check(matches(isDisplayed()));
        onView(withId(R.id.event_description_input)).check(matches(isDisplayed()));

        onView(withId(R.id.event_add_people)).check(matches(isDisplayed()));
        onView(withId(R.id.create_event_button)).perform(scrollTo());

        onView(withText("Create Event")).check(matches(isDisplayed()));
        onView(withId(R.id.back_button)).check(matches(isDisplayed()));
    }
}
