package example.subpages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.example.exp3.MainActivity2;
import com.example.exp3.R;

public class MyAccount extends AppCompatActivity {
    private Button goBackButton;
    private Button changePFPButton;
    private ImageView profileImageView;
    private static final int RESULT_LOAD_IMG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        // Initialize
        goBackButton = findViewById(R.id.gobackbutton);
        changePFPButton = findViewById(R.id.changeImageButton);
        profileImageView = findViewById(R.id.profileImageView);

        // Load the default square profile picture
        profileImageView.setImageResource(R.drawable.default_profile_image);
        // Apply the circular mask
        profileImageView.setBackgroundResource(R.drawable.circular_background);

        // Set a click listener for the goBackButton button
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // This will handle the button click.
                Intent intent = new Intent(MyAccount.this, MainActivity2.class);
                startActivity(intent); // Start MainActivity2 need this other wise im just calling an object lol
            }
        });

        // Set a click listener for the changePFPButton button
        changePFPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                // Start the image picker activity
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
    }

    // Handle the result of the image picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Checks if the requestCode matches the constant RESULT_LOAD_IMG the resultCode is RESULT_OK, and the data is not null.
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            Uri selectedImageUri = data.getData();

            try {
                // Load and display the selected image in the ImageView
                profileImageView.setImageURI(selectedImageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}