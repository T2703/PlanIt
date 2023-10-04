package planIT.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Melani Hodge
 *
 */

@RestController
public class UserController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private UserService userService;

    // GET method - retreives all users from the database.
    @GetMapping(path = "/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET method - retreives a user from the database.
    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // POST method - adds a user to the database.
    @PostMapping(path = "/users")
    public String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // PUT method - updates a user in the database.
    @PutMapping(path = "/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // DELETE method - deletes a user from the database.
    @DeleteMapping(path = "/users/{id}")
    public String deleteUser(@PathVariable int id) {
        return userService.deleteUser(id);
    }
}

