package profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.NavBar;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import homepage.HomePage;

public class LoginFormPage extends AppCompatActivity {

    private TextView create_account_button;
    private Button login_button;

    /*
    The email that the user will use for logging in.
     */
    private EditText user_email;

    /*
    The password that the user will use.
     */
    private EditText user_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        create_account_button = findViewById(R.id.create_account_button);
        login_button = findViewById(R.id.login_button);
        login_button.setEnabled(false); // Set this to false for checking the inputs of the user.
        user_email = findViewById(R.id.login_username_field);
        user_password = findViewById(R.id.login_password_field);

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
                Intent intent = new Intent(LoginFormPage.this, HomePage.class);
                startActivity(intent);
            }
        });

        // This is for the user's email to see if anything is in it or not.
        user_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // This is for the user's password to see if anything is in it or not.
        user_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


    }

    /*
    This checks for empty values in the edit text variables so in other words,
    if there's nothing in both the email and password then, it shouldn't go through.
    */
    private void checkFieldsForEmptyValues() {
        String user_email_login = user_email.getText().toString();
        String password = user_password.getText().toString();

        if (!user_email_login.isEmpty() && !password.isEmpty()) {
            login_button.setEnabled(true);
        }

        else {
            login_button.setEnabled(false);
        }
    }
}
