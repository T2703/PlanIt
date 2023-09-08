package com.example.exp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    // Define the method to be called when the FloatingActionButton is clicked
    public void onSettingsButtonClick(View view) {
        // Implement your button's functionality here
        // For example, open a settings activity or perform some action
        // when the button is clicked.
        Log.d("BUTTONS", "User tapped");
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent); // Start MainActivity2 need this other wise im just calling an object lol
    }

}