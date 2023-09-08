package com.example.exp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }


    // Define the method to be called when the FloatingActionButton is clicked same thing but we just use the "home" button
    public void onHomeButtonClick(View view) {
        Log.d("BUTTONS", "User tapped");
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent); // Start MainActivity2 need this other wise im just calling an object lol
    }
}