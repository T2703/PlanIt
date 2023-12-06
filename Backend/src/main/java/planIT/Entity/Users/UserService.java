package planIT.Entity.Users;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service class for managing User entities.
 * This class handles business logic related to user operations, interacting with the UserRepository.
 * It is annotated with @Service to denote it as a service component, and @Transactional to enable transactional actions.
 *
 * @author Melani Hodge
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Retrieves all users from the repository.
     *
     * @return List of User entities.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a specific user from the repository based on the provided ID.
     *
     * @param id The unique identifier of the user.
     * @return The User entity corresponding to the provided ID.
     */
    public User getUserById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Creates a new user in the repository.
     *
     * @param user The User entity to be created.
     * @return A success or failure message as a JSON string.
     */
    public String createUser(User user) {
        userRepository.save(user);
        return success;
    }

    /**
     * Updates an existing user in the repository based on the provided ID.
     *
     * @param id      The unique identifier of the user to be updated.
     * @param request The updated User entity.
     * @return The updated User entity or null if the user with the provided ID is not found.
     */
    public String updateUser(int id, User request) {
        User user = userRepository.findById(id);
        if (user == null)
            return failure;

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        userRepository.save(user);
        return success;
    }

    public String addFollower(String name1, String name2) {
        User user = userRepository.findByUsername(name1);
        User follower = userRepository.findByUsername(name2);

        user.getFollowing().add(follower);
        userRepository.save(user);

        follower.getFollowers().add(user);
        userRepository.save(follower);

        return success;
    }

    public String removeFollower(String userUsername, String followerUsername) {
        User user = userRepository.findByUsername(userUsername);
        User follower = userRepository.findByUsername(followerUsername);

        user.getFollowing().remove(follower);
        follower.getFollowers().remove(user);

        userRepository.save(user);
        userRepository.save(follower);

        return success;
    }

    /**
     * Deletes a user from the repository based on the provided ID.
     *
     * @param id The unique identifier of the user to be deleted.
     * @return A success or failure message as a JSON string.
     */
    public String deleteUser(int id) {
        userRepository.deleteById(id);
        return success;
    }

    /**
     * Finds a user in the repository based on the provided username.
     *
     * @param username The username of the user.
     * @return The User entity corresponding to the provided username.
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
