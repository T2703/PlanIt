package planIT.Entity.ToDos;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing To-Do entities using Spring Data JPA.
 * This interface extends JpaRepository, providing basic CRUD operations for To-Do entities.
 * Additionally, it includes custom query methods for finding To-Dos by ID and deleting a To-Do by ID.
 *
 * @author Chris Smith
 *
 */
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    /**
     * Finds a To-Do in the repository based on the provided ID.
     *
     * @param id The unique identifier of the To-Do.
     * @return The ToDo entity corresponding to the provided ID.
     */
    ToDo findById(int id);

    /**
     * Deletes a To-Do from the repository based on the provided ID.
     *
     * @param id The unique identifier of the To-Do to be deleted.
     */
    void deleteById(int id);
}
