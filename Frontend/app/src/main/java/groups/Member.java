// Author: Tristan Nono

package groups;

/*
A member for the list of members.
 */
public class Member {
    /*
    The username.
     */
    private String username;

    /*
    Group's ID number.
    */
    private String group_id;

    /*
    Group's name.
     */
    private String group_name;

    /*
    Group's description.
    */
    private String description;

    /*
    Constructor for member.
     */
    public Member(String group_name, String description, String group_id) {
        //this.username = username;
        this.group_name = group_name;
        this.description = description;
        this.group_id = group_id;
    }

    /*
    Get the username.
     */
    public String getUsername() {
        return username;
    }

    /*
    Get the group's name.
    */
    public String getGroupName() {
        return group_name;
    }

    /*
    Get the description.
    */
    public String getDescription() {
        return description;
    }

    /*
    Get the group ID.
    */
    public String getGroupId() {
        return group_id;
    }



}
