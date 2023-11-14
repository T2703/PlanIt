package groups;

/**
 * Represents a group.
 * Information about the group such as its name, description, and ID.
 *
 * @author Tristan Nono
 */
public class Member {
    /**
     * The username (currently commented out and not used).
     */
    private String username;

    /**
     * Group's ID number.
     */
    private String group_id;

    /**
     * Group's name.
     */
    private String group_name;

    /**
     * Group's description.
     */
    private String description;

    /**
     * Constructor for the Member class.
     *
     * @param group_name The name of the group.
     * @param description The description of the group.
     * @param group_id The ID number of the group.
     */
    public Member(String group_name, String description, String group_id) {
        //this.username = username;
        this.group_name = group_name;
        this.description = description;
        this.group_id = group_id;
    }

    /**
     * Get the username (currently not used).
     *
     * @return The username (may be null or an empty string).
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get the name of the group.
     *
     * @return The name of the group.
     */
    public String getGroupName() {
        return group_name;
    }

    /**
     * Get the description of the group.
     *
     * @return The description of the group.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the ID number of the group.
     *
     * @return The ID number of the group.
     */
    public String getGroupId() {
        return group_id;
    }



}
