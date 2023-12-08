package UI_Testing;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.contrib.PickerActions;

import static org.hamcrest.Matchers.allOf;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.myapplication.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import events.CreateEventPage;
import events.EventsListViewer;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CreateEventPageTest {
    @Rule
    public ActivityScenarioRule<CreateEventPage> activityRule = new ActivityScenarioRule<>(CreateEventPage.class);

    @Before
    public void setUp() {
        Intents.init();
    }

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

    @Test
    public void testFormCompletion() {
        onView(withId(R.id.event_name_text)).perform(typeText("My Event Test"));

        String eventType = "Private";

        onView(withId(R.id.event_type_dropdown)).perform(click());
        onView(withText(eventType)).perform(click());

        onView(withId(R.id.start_date_input)).perform(click());
        onView(allOf(isAssignableFrom(DatePicker.class))).perform(PickerActions.setDate(2023, 12, 7));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.end_date_input)).perform(click());
        onView(allOf(isAssignableFrom(DatePicker.class))).perform(PickerActions.setDate(2023, 12, 7));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.start_time_input)).perform(click());
        onView(allOf(isAssignableFrom(TimePicker.class))).perform(PickerActions.setTime(12, 30));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.create_event_button)).perform(scrollTo());

        onView(withId(R.id.end_time_input)).perform(click());
        onView(allOf(isAssignableFrom(TimePicker.class))).perform(PickerActions.setTime(13, 30));
        onView(withText("OK")).perform(click());

        onView(withId(R.id.create_event_button)).perform(scrollTo());

        onView(withId(R.id.event_location_input)).perform(typeText("My Event Location Test"));
        onView(withId(R.id.event_description_input)).perform(typeText("My Event Description Test"));

        onView(withId(R.id.create_event_button)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(EventsListViewer.class.getName()));
    }

    @After
    public void cleanUp() {
        Intents.release();
    }
}