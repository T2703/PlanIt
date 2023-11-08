package planIT.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import planIT.Entity.Users.User;
import planIT.Entity.Users.UserService;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity authentication(@RequestBody LoginRequest loginRequest) {

        System.out.println("user login: " + loginRequest.getUsername());

        User user = userService.findUserByUsername(loginRequest.getUsername());

        boolean check_password = BCrypt.checkpw(loginRequest.getPassword(), user.getPassword());

        if (user == null || !check_password) {
            return new ResponseEntity("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(user, HttpStatus.OK);
    }
}
