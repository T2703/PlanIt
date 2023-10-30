package homepage;

public class Assignment {
    private String assignmentClass;
    private String assignmentTitle;
    private String assignmentDueDate;

    public Assignment(String assignmentClass, String assignmentTitle, String assignmentDueDate) {
        this.assignmentClass = assignmentClass;
        this.assignmentTitle = assignmentTitle;
        this.assignmentDueDate = assignmentDueDate;
    }

    public String getAssignmentClass() {
        return assignmentClass;
    }
    public String getAssignmentTitle() {
        return assignmentTitle;
    }
    public String getDueDate() {
        return assignmentDueDate;
    }
}
