package planIT.Entity.Teams;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Melani Hodge
 *
 */

// Initializes the Team repository.
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findById(int id);
    void deleteById(int id);

}
