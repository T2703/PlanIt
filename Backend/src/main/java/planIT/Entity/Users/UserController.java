package planIT.Entity.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import planIT.Login.LoginRequest;
import planIT.Login.Password;

/**
 * RESTful controller for managing user-related operations.
 * This controller handles HTTP requests related to user entities, such as retrieval, creation, update, and deletion.
 * Requests are processed using the corresponding methods provided by the UserService.
 *
 * @author Melani Hodge
 */
@RestController
@Tag(name = "User Management System", description = "Operations pertaining to user management")
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
    @Operation(summary = "View a list of all users", description = "Retrieves all users from the database")
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
    @Operation(summary = "Get a user by Id", description = "Retrieves a specific user from the database based on the provided ID")
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
    @Operation(summary = "Get a user by username", description = "Retrieves a specific user from the database based on the provided username")
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
    @Operation(summary = "Add a new user", description = "Adds a new user to the database")
    public String createUser(@RequestBody User user) {

        // Check if all the fields are filled out
        if (user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            return "Please complete all fields.";
        }

        if(userService.findUserByUsername(user.getUsername()) != null){
            return "Username already taken.";
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
    @Operation(summary = "Update an existing user", description = "Updates an existing user in the database")
    public String updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    /**
     * Deletes a user from the database based on the provided ID.
     *
     * @param id The unique identifier of the user to be deleted.
     * @return A success or failure message as a JSON string.
     */
    @DeleteMapping(path = "/users/{id}")
    @Operation(summary = "Delete a user by Id", description = "Deletes a user from the database based on the provided ID")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/change-password/{username}")
    public String changePassword(@PathVariable String username, @RequestBody Password password) {

        User user = userService.findUserByUsername(username);

        String hashed_password = BCrypt.hashpw(password.getPassword(), BCrypt.gensalt());

        user.setPassword(hashed_password);

        userService.updateUser(user.getId(), user);

        return success;
    }

    @PostMapping("/login")
    public ResponseEntity authentication(@RequestBody LoginRequest loginRequest) {

        System.out.println("user login: " + loginRequest.getUsername());

        User user = userService.findUserByUsername(loginRequest.getUsername());

        boolean check_password = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());

        if (user == null || !check_password) {
            return new ResponseEntity("Incorrect Username or Password.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(user, HttpStatus.OK);
    }
}

