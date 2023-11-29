package planIT.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import planIT.Entity.Users.User;
import planIT.Entity.Users.UserService;

/**
 * Controller class for handling user authentication requests in the PlanIT application.
 * Exposes an endpoint for user login, where provided credentials are verified against
 * stored user information. Uses Spring's RestController annotation to indicate
 * that this class handles RESTful requests.
 *
 * The authentication process involves checking the submitted username and password
 * against the stored user data. If successful, it returns the user information in
 * a ResponseEntity with HttpStatus.OK; otherwise, it returns an error message with
 * HttpStatus.BAD_REQUEST.
 *
 * @author Melani Hodge
 *
 */
@RestController
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    private String success = "{\"message\":\"success\"}";

    /**
     * Handles user authentication requests.
     * Verifies the provided credentials against the stored user information.
     *
     * @param username The username of the user changing their password.
     * @param password The password of the user changing their password.
     * @return ResponseEntity with user information if authentication is successful,
     *         or an error message with HttpStatus.BAD_REQUEST if authentication fails.
     */
    @PostMapping("/change-password/{username}")
    public String authentication(@PathVariable String username, @RequestBody String password) {

        User user = userService.findUserByUsername(username);

        String hashed_password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        user.setPassword(hashed_password);

        return success;
    }
}
