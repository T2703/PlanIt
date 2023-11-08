package planIT.Entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Melani Hodge
 *
 */

// Initializes the User repository.
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);

    User findByUsername(String username);

    void deleteById(int id);

}
