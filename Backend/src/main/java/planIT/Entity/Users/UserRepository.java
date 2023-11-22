package planIT.Entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing User entities using Spring Data JPA.
 * This interface extends JpaRepository, providing basic CRUD operations for User entities.
 * Additionally, it includes custom query methods for finding users by ID and username,
 * as well as a method for deleting a user by ID.
 *
 * @author Melani Hodge
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user in the repository based on the provided ID.
     *
     * @param id The unique identifier of the user.
     * @return The User entity corresponding to the provided ID.
     */
    User findById(int id);

    /**
     * Finds a user in the repository based on the provided username.
     *
     * @param username The username of the user.
     * @return The User entity corresponding to the provided username.
     */
    User findByUsername(String username);

    /**
     * Deletes a user from the repository based on the provided ID.
     *
     * @param id The unique identifier of the user to be deleted.
     */
    void deleteById(int id);

}
