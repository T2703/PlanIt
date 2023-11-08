package planIT.Entity.Users;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Melani Hodge
 *
 */

// @Service - Used to denote a service.
// @Transactional - Used to allow transactional actions on the server.
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    public String createUser(User user) {
        userRepository.save(user);
        return success;
    }

    public User updateUser(int id, User request) {
        User user = userRepository.findById(id);
        if (user == null)
            return null;

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());

        userRepository.save(user);
        return userRepository.findById(id);
    }

    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return success;
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
