package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import profile.CreateAccountPage;
import profile.LoginFormPage;

/*
    This class represents the page an unsigned user will see,
    not to be confused by the homepage once the user is signed in
 */
public class Index extends AppCompatActivity {

    private Button login_button;
    private TextView create_account_link;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_login);

        login_button = findViewById(R.id.homepage_login_button);
        create_account_link = findViewById(R.id.create_account_button);

        // Navigate to login form
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Index.this, LoginFormPage.class);
                startActivity(intent);
            }
        });

        // Navigate to create account form
        create_account_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Index.this, CreateAccountPage.class);
                startActivity(intent);
            }
        });
    }
}
