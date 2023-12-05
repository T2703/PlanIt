package planIT.Login;

/**
 * A data class representing a user login request.
 * It encapsulates the username and password submitted during the login process.
 *
 * @author Melani Hodge
 *
 */
public class Password {

    private String password;

    public Password(String password) {
        this.password = password;
    }

    public Password() {

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
