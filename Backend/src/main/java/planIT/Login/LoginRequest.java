package planIT.Login;

/**
 * A data class representing a user login request.
 * It encapsulates the username and password submitted during the login process.
 *
 * @author Melani Hodge
 *
 */
public class LoginRequest {

    private String username;
    private String password;

    /**
     * Gets the username from the login request.
     *
     * @return The username submitted in the login request.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username in the login request.
     *
     * @param username The username to be set in the login request.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password from the login request.
     *
     * @return The password submitted in the login request.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password in the login request.
     *
     * @param password The password to be set in the login request.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
