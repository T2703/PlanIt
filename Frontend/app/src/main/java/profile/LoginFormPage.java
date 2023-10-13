package profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.NavBar;
import com.example.myapplication.R;

public class LoginFormPage extends AppCompatActivity {

    private TextView create_account_button;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        create_account_button = findViewById(R.id.create_account_button);
        login_button = findViewById(R.id.login_button);

        create_account_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFormPage.this, CreateAccountPage.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginFormPage.this, NavBar.class);
                startActivity(intent);
            }
        });
    }
}
