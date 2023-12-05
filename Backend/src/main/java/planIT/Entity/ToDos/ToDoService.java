package planIT.Entity.ToDos;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import planIT.Entity.Users.*;

/**
 * Service class for managing To-Do entities.
 * This class handles business logic related to To-Do operations, interacting with the ToDoRepository and UserRepository.
 * It is annotated with @Service to denote it as a service component, and @Transactional to enable transactional actions.
 *
 * @author Chris Smith
 *
 */
@Service
@Transactional
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    /**
     * Retrieves all To-Dos from the repository.
     *
     * @return List of ToDo entities.
     */
    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }

    /**
     * Retrieves a specific To-Do from the repository based on the provided ID.
     *
     * @param id The unique identifier of the To-Do.
     * @return The ToDo entity corresponding to the provided ID.
     */
    public ToDo getToDoById(int id) {
        return toDoRepository.findById(id);
    }

    /**
     * Creates a new To-Do in the repository.
     *
     * @param toDo The ToDo entity to be created.
     * @return A success or failure message as a JSON string.
     */
    public String createToDo(ToDo toDo) {
        toDoRepository.save(toDo);
        return success;
    }

    /**
     * Updates an existing To-Do in the repository based on the provided ID.
     *
     * @param id      The unique identifier of the To-Do to be updated.
     * @param request The updated ToDo entity.
     * @return The updated ToDo entity or null if the To-Do with the provided ID is not found.
     */
    public String updateToDo(int id, ToDo request) {
        ToDo toDo = toDoRepository.findById(id);
        if (toDo == null)
            return failure;

        toDo.setName(request.getName());
        toDo.setDescription(request.getDescription());
        toDo.setRemindTime(request.getRemindTime());
        toDo.setDueDate(request.getDueDate());

        toDoRepository.save(toDo);
        return success;
    }

    /**
     * Deletes a To-Do from the repository based on the provided ID.
     *
     * @param id The unique identifier of the To-Do to be deleted.
     * @return A success or failure message as a JSON string.
     */
    public String deleteToDo(int id) {
        toDoRepository.deleteById(id);
        return success;
    }

    /**
     * Adds a To-Do to a specific user.
     *
     * @param username The username of the user.
     * @param toDo     The ToDo entity to be added.
     * @return A success or failure message as a JSON string.
     */
    public String userAddToDo(String username, ToDo toDo){
        User user = userRepository.findByUsername(username);
        toDoRepository.save(toDo);
        user.getToDos().add(toDo);
        toDo.setUser(user);

        return success;
    }
}
