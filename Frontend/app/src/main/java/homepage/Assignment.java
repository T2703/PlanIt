package homepage;

/**
 * A class representing an assignment.
 *
 * @author Joshua Gutierrez
 *
 * <p>Provides information about the assignment, including the class it belongs to,
 * its title, and the due date.
 * </p>
 */
public class Assignment {
    /**
     * The class to which the assignment belongs.
     */
    private String assignmentClass;

    /**
     * The title of the assignment.
     */
    private String assignmentTitle;

    /**
     * The due date of the assignment.
     */
    private String assignmentDueDate;

    /**
     * Constructs an assignment with the given parameters.
     *
     * @param assignmentClass The class to which the assignment belongs.
     * @param assignmentTitle The title of the assignment.
     * @param assignmentDueDate The due date of the assignment.
     */
    public Assignment(String assignmentClass, String assignmentTitle, String assignmentDueDate) {
        this.assignmentClass = assignmentClass;
        this.assignmentTitle = assignmentTitle;
        this.assignmentDueDate = assignmentDueDate;
    }

    /**
     * Gets the class to which the assignment belongs.
     *
     * @return The assignment class.
     */
    public String getAssignmentClass() {
        return assignmentClass;
    }

    /**
     * Gets the title of the assignment.
     *
     * @return The assignment title.
     */
    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    /**
     * Gets the due date of the assignment.
     *
     * @return The due date.
     */
    public String getDueDate() {
        return assignmentDueDate;
    }
}
