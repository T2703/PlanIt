package notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import homepage.HomePage;

/**
 * The {@code NotificationPage} class represents the activity for displaying a list of notifications.
 * It extends the AppCompatActivity and provides functionality for handling notifications,
 * including selecting all notifications and navigating back to the home page.
 * <p>
 * The activity uses a RecyclerView to display a list of notifications, and it utilizes the
 * {@code NotificationAdapter} class to manage the adapter for the RecyclerView.
 * </p>
 *
 * @see AppCompatActivity
 * @see NotificationAdapter
 * @see Notification
 * @author Joshua Gutierrez
 * @version 1.0
 */
public class NotificationPage extends AppCompatActivity {
    private ImageButton backButton;
    private RecyclerView notificationItemRecycler;
    private NotificationAdapter adapter;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling {@code setContentView(int)} to inflate the activity's UI, using
     * {@code findViewById(int)} to programmatically interact with widgets in the UI,
     * and configuring the activity's behavior as needed.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, then this Bundle contains the data it most recently
     *                           supplied in {@link #onSaveInstanceState}. Note: Otherwise, it is
     *                           null.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);

        backButton = findViewById(R.id.back_button);

        notificationItemRecycler = findViewById(R.id.notificationsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notificationItemRecycler.setLayoutManager(layoutManager);

        List<Notification> notificationsList = createNotificationList();
        adapter = new NotificationAdapter(notificationsList);
        notificationItemRecycler.setAdapter(adapter);

        // Select all
        CheckBox selectAllCheckbox = findViewById(R.id.selectAllCheckbox);

        selectAllCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < notificationItemRecycler.getAdapter().getItemCount(); i++) {
                    Notification notification = notificationsList.get(i);

                    notification.setIsSelected(!notification.getIsSelected());
                }

                notificationItemRecycler.getAdapter().notifyDataSetChanged();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationPage.this, HomePage.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Creates a list of sample notifications for testing purposes.
     *
     * @return A list of {@code Notification} objects.
     */
    private List<Notification> createNotificationList() {
        List<Notification> notifications = new ArrayList<>();

        notifications.add(new Notification("Demo 4", "You have been invited to a private event", true));
        notifications.add(new Notification("Demo 5", "You have been invited to a private event", true));
        notifications.add(new Notification("Demo 6", "You have been invited to a private event", true));
        notifications.add(new Notification("Demo 6", "You have been invited to a private event", true));
        notifications.add(new Notification("Demo 6", "You have been invited to a private event", true));
        notifications.add(new Notification("Demo 6", "You have been invited to a private event", true));
        notifications.add(new Notification("Demo 6", "You have been invited to a private event", true));

        return notifications;
    }
}
