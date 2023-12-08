package homepage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import java.util.List;

/**
 * Adapter class for displaying a list of assignments in a RecyclerView.
 *
 * @author Joshua Gutierrez
 *
 * <p>The adapter binds assignment data to the corresponding views in the RecyclerView.</p>
 */
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    /**
     * List of assignments to be displayed.
     */
    private List<Assignment> assignments;
    private TextView message;

    /**
     * Constructs an AssignmentAdapter with the given list of assignments.
     *
     * @param assignments The list of assignments to be displayed.
     */
    public AssignmentAdapter(List<Assignment> assignments, TextView message) {
        this.assignments = assignments;
        this.message = message;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to represent
     * an assignment view.
     *
     * @param parent The parent view group that will contain the new view.
     * @param viewType The view type of the new View.
     * @return A new AssignmentViewHolder that holds the assignment view.
     */
    @NonNull
    @Override
    public AssignmentAdapter.AssignmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_assignments, parent, false);
        return new AssignmentAdapter.AssignmentViewHolder(itemView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder The AssignmentViewHolder that should be updated to represent
     *               the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.AssignmentViewHolder holder, int position) {
        if (assignments.size() != 0) {
            Assignment assignment = assignments.get(position);
            holder.assignmentClass.setText(assignment.getAssignmentClass());
            holder.assignmentTitle.setText(assignment.getAssignmentTitle());
            holder.assignmentDueDate.setText(assignment.getDueDate());
        } else {
            message.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Returns the total number of assignments in the data set held by the adapter.
     *
     * @return The total number of assignments.
     */
    @Override
    public int getItemCount() {
        return assignments.size();
    }

    /**
     * ViewHolder class representing an assignment view in the RecyclerView.
     */
    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentClass, assignmentTitle, assignmentDueDate;

        /**
         * Constructs an AssignmentViewHolder with the given item view.
         *
         * @param itemView The view representing an assignment.
         */
        public AssignmentViewHolder(View itemView) {
            super(itemView);
            assignmentClass = itemView.findViewById(R.id.assignmentClass);
            assignmentTitle = itemView.findViewById(R.id.assignmentTitle);
            assignmentDueDate = itemView.findViewById(R.id.assignmentDueDate);
        }
    }
}
