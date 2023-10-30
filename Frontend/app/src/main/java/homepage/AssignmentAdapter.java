package homepage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private List<Assignment> assignments;

    public AssignmentAdapter(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    @NonNull
    @Override
    public AssignmentAdapter.AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_assignments, parent, false);
        return new AssignmentAdapter.AssignmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.AssignmentViewHolder holder, int position) {
        Assignment assignment = assignments.get(position);
        holder.assignmentClass.setText(assignment.getAssignmentClass());
        holder.assignmentTitle.setText(assignment.getAssignmentTitle());
        holder.assignmentDueDate.setText(assignment.getDueDate());
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentClass, assignmentTitle, assignmentDueDate;

        public AssignmentViewHolder(View itemView) {
            super(itemView);
            assignmentClass = itemView.findViewById(R.id.assignmentClass);
            assignmentTitle = itemView.findViewById(R.id.assignmentTitle);
            assignmentDueDate = itemView.findViewById(R.id.assignmentDueDate);
        }
    }
}
