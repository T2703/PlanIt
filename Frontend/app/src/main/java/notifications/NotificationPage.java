package notifications;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import homepage.HomePage;

public class NotificationPage extends AppCompatActivity {
    private ImageButton backButton;
    private RecyclerView notificationItemRecycler;
    private NotificationAdapter adapter;
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
