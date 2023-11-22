package planIT.Entity.Assignments;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for assignment entities
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    /**
     * Finds an assignment by its id number
     * @param id id number of assignment to find
     * @return Assignment
     */
    Assignment findById(int id);

    /**
     * Deletes an assignment from the repository
     * @param id id number of the assignment to be deleted
     */
    void deleteById(int id);

}
