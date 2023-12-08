package assignments;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import api.VolleySingleton;
import homepage.HomePage;
import websockets.WebSocketManager;

public class AssignmentsPage extends AppCompatActivity {
    private static final String PUT_REQUEST_URL = "http://coms-309-024.class.las.iastate.edu:8080/users/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments_page);

        ImageButton backButton = findViewById(R.id.assignments_back_button);
        Button connectButton = findViewById(R.id.connect_canvas_method);
        EditText tokenField = findViewById(R.id.assignment_token);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssignmentsPage.this, HomePage.class);
                ActivityOptions options = ActivityOptions.makeCustomAnimation(AssignmentsPage.this, R.anim.empty_anim, R.anim.empty_anim);
                startActivity(intent, options.toBundle());
            }
        });

        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String tokenFieldString = tokenField.getText().toString();

                JSONObject requestBody = new JSONObject();

                try {
                    requestBody.put("canvasToken", tokenFieldString);
                    WebSocketManager.getInstance().setAccessToken(tokenFieldString);
                    putRequest(requestBody);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                Intent intent = new Intent(AssignmentsPage.this, AssignmentsListPage.class);
                startActivity(intent);
            }
        });
    }

    public void putRequest(JSONObject requestBody) {
        String username = WebSocketManager.getInstance().getUsername();

        JsonObjectRequest stringRequest = new JsonObjectRequest(
                Request.Method.PUT,
                PUT_REQUEST_URL + username + "/set-canvas-token",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "You have been connected succesfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("A server error has occurred", error.toString());
                    }
                }
        );
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
