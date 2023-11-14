package planIT.Entity.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * RESTful controller for managing user-related operations.
 * This controller handles HTTP requests related to user entities, such as retrieval, creation, update, and deletion.
 * Requests are processed using the corresponding methods provided by the UserService.
 *
 * @author Melani Hodge
 */
@RestController
public class UserController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private UserService userService;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Retrieves all users from the database.
     *
     * @return List of User entities.
     */
    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Retrieves a specific user from the database based on the provided ID.
     *
     * @param id The unique identifier of the user.
     * @return The User entity corresponding to the provided ID.
     */
    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * Retrieves a specific user from the database based on the provided username.
     *
     * @param username The username of the user.
     * @return The User entity corresponding to the provided username.
     */
    @GetMapping(path = "/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

    /**
     * Adds a new user to the database.
     *
     * @param user The User entity to be added.
     * @return A success or failure message as a JSON string.
     */
    @PostMapping(path = "/users")
    public String createUser(@RequestBody User user) {

        // Check if all the fields are filled out
        if (user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return "Please complete all fields.";
        }

        // Hash the password
        String hashed_password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        User hashed_user = new User(user.getUsername(), hashed_password, user.getEmail());

        return userService.createUser(hashed_user);
    }

    /**
     * Updates an existing user in the database.
     *
     * @param id   The unique identifier of the user to be updated.
     * @param user The updated User entity.
     * @return The updated User entity.
     */
    @PutMapping(path = "/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    /**
     * Deletes a user from the database based on the provided ID.
     *
     * @param id The unique identifier of the user to be deleted.
     * @return A success or failure message as a JSON string.
     */    @DeleteMapping(path = "/users/{id}")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }
}

