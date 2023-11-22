package homepage;

/**
 * @author Joshua Gutierrez
 * The User class represents a user entity with an avatar and online status.
 * It is used to store and retrieve information about users in the application.
 */
public class User {
    private int avatarResource;
    private boolean isOnline;

    /**
     * Constructs a new User instance with the specified avatar resource and online status.
     *
     * @param avatarResource The resource ID of the user's avatar image.
     * @param isOnline       The online status of the user (true if online, false otherwise).
     */
    public User(int avatarResource, boolean isOnline) {
        this.avatarResource = avatarResource;
        this.isOnline = isOnline;
    }

    /**
     * Retrieves the resource ID of the user's avatar image.
     *
     * @return The resource ID of the user's avatar image.
     */
    public int getAvatarResource() {
        return avatarResource;
    }

    /**
     * Checks if the user is currently online.
     *
     * @return True if the user is online, false otherwise.
     */
    public boolean isOnline() {
        return isOnline;
    }
}
