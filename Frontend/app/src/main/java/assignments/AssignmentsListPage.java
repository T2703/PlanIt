package assignments;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import homepage.Assignment;
import homepage.AssignmentAdapter;
import utilities.AssignmentsHelper;

public class AssignmentsListPage extends AppCompatActivity {
    private RecyclerView assignmentsRecyclerView;
    private AssignmentAdapter assignmentsAdapter;
    private List<Assignment> assignmentsList = AssignmentsHelper.assignments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments_list);

        AssignmentsHelper.getCoursesIds(this);

        assignmentsRecyclerView = findViewById(R.id.assignmentListRecyclerView);

        AssignmentsHelper.populateAssignments(this);

        getAssignmentsFromDatabase();

        assignmentsAdapter = new AssignmentAdapter(assignmentsList, new TextView(this));
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);
    }

    public void getAssignmentsFromDatabase() {
        RequestQueue queue = Volley.newRequestQueue(this);

        // Define the URL of your API endpoint.
        String url = "http://coms-309-024.class.las.iastate.edu:8080/users/josh/assignments";

        // Create a JSON array request.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                if (jsonObject.getBoolean("isCompleted") != true) {
                                    assignmentsList.add(new Assignment(jsonObject.getString("course"), jsonObject.getString("title"), jsonObject.getString("dueDate")));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "An error occurred. Please refresh.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }
}
