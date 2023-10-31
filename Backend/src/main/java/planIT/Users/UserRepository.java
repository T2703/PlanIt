package planIT.Users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Melani Hodge
 *
 */

// Initializes the User repository.
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);

    User findByUsernameAndPassword(String username, String password);

    void deleteById(int id);

}
