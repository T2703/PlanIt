package followers;

/**
 * Attributes for the user
 */
public class UserFollower {
    /**
     * The unique identifier for the user.
     */
    private String id;

    /**
     * The username.
     */
    private String username;

    /**
     * Is followed?
     */
    private boolean isFollowed;

    public UserFollower(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getID() {
        return id;
    }

    public String getUserName() {
        return username;
    }


}
