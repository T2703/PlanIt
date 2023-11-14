package planIT.Entity.ToDos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import planIT.Entity.Assignments.Assignment;

/**
 * RESTful controller for managing To-Do-related operations.
 * This controller handles HTTP requests related to To-Do entities, such as retrieval, creation, update, and deletion.
 * Requests are processed using the corresponding methods provided by the ToDoService.
 * Additionally, it includes a method for adding a To-Do to a specific user.
 *
 * @author Chris Smith
 *
 */
@RestController
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    /**
     * Retrieves all To-Dos from the service.
     *
     * @return List of ToDo entities.
     */
    @GetMapping(path = "/ToDos")
    public List<ToDo> getAllToDos(){
        return toDoService.getAllToDos();
    }

    /**
     * Retrieves a specific To-Do from the service based on the provided ID.
     *
     * @param id The unique identifier of the To-Do.
     * @return The ToDo entity corresponding to the provided ID.
     */
    @GetMapping(path = "/ToDos/{id}")
    public ToDo getEventById(@PathVariable int id){
        return toDoService.getToDoById(id);
    }

    /**
     * Creates a new To-Do in the service.
     *
     * @param toDo The To-Do entity to be created.
     * @return A success or failure message as a JSON string.
     */
    @PostMapping(path = "/ToDos")
    public String createToDo(@RequestBody ToDo toDo){
        return toDoService.createToDo(toDo);
    }

    /**
     * Adds a To-Do to a specific user.
     *
     * @param username The username of the user.
     * @param toDo     The ToDo entity to be added.
     * @return A success or failure message as a JSON string.
     */
    @PostMapping(path = "users/{username}/ToDos")
    public String userAddToDo(@PathVariable String username, @RequestBody ToDo toDo){
        return toDoService.userAddToDo(username, toDo);
    }

    /**
     * Updates an existing To-Do in the service based on the provided ID.
     *
     * @param id   The unique identifier of the To-Do to be updated.
     * @param toDo The updated ToDo entity.
     * @return The updated ToDo entity.
     */
    @PutMapping(path = "/ToDos/{id}")
    public ToDo updateToDo(@PathVariable int id, @RequestBody ToDo toDo){
        return toDoService.updateToDo(id, toDo);
    }

    /**
     * Deletes a To-Do from the service based on the provided ID.
     *
     * @param id The unique identifier of the To-Do to be deleted.
     * @return A success or failure message as a JSON string.
     */
    @DeleteMapping(path = "/ToDos/{id}")
    public String deleteToDo(@PathVariable int id){
        return toDoService.deleteToDo(id);
    }
}
