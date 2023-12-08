package assignments;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

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

        assignmentsAdapter = new AssignmentAdapter(assignmentsList, new TextView(this));
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        assignmentsRecyclerView.setAdapter(assignmentsAdapter);
    }
}
