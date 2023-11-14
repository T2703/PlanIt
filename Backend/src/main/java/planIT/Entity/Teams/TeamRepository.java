package planIT.Entity.Teams;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Team entities using Spring Data JPA.
 * This interface extends JpaRepository, providing basic CRUD operations for Team entities.
 * Additionally, it includes custom query methods for finding Teams by ID and deleting a Team by ID.
 *
 * @author Melani Hodge
 *
 */
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     * Finds a Team in the repository based on the provided ID.
     *
     * @param id The unique identifier of the Team.
     * @return The Team entity corresponding to the provided ID.
     */
    Team findById(int id);

    /**
     * Deletes a Team from the repository based on the provided ID.
     *
     * @param id The unique identifier of the Team to be deleted.
     */
    void deleteById(int id);

}
