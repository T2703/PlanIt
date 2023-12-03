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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserService;

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
@Tag(name = "To-Do Management System", description = "Operations pertaining to To-Do management")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private UserService userService;

    /**
     * Retrieves all To-Dos from the service.
     *
     * @return List of ToDo entities.
     */
    @GetMapping(path = "/ToDos")
    @Operation(summary = "Get all To-Dos", description = "Returns all To-Dos from the repository as a List object")
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
    @Operation(summary = "Get a To-Do by Id", description = "Gets a To-Do from the repository based on id number")
    public ToDo getEventById(@PathVariable int id){
        return toDoService.getToDoById(id);
    }

    /**
     * Adds a To-Do to a specific user.
     *
     * @param username The username of the user.
     * @param toDo     The ToDo entity to be added.
     * @return A success or failure message as a JSON string.
     */
    @PostMapping(path = "users/{username}/ToDos")
    @Operation(summary = "Add a To-Do to a user", description = "Adds a To-Do to a user in the repository")
    public String createToDo(@PathVariable String username, @RequestBody ToDo toDo){
        toDoService.createToDo(toDo);

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
    @Operation(summary = "Update an existing To-Do", description = "Updates a To-Do in the repository")
    public String updateToDo(@PathVariable int id, @RequestBody ToDo toDo){
        return toDoService.updateToDo(id, toDo);
    }

    /**
     * Deletes a To-Do from the service based on the provided ID.
     *
     * @param id The unique identifier of the To-Do to be deleted.
     * @return A success or failure message as a JSON string.
     */
    @DeleteMapping(path = "users/{username}/ToDos/{id}")
    @Operation(summary = "Delete a To-Do by Id", description = "Deletes a To-Do from the repository")
    public String deleteToDo(@PathVariable String username, @PathVariable int id){
        User user = userService.findUserByUsername(username);
        user.getToDos().remove(toDoService.getToDoById(id));
        return toDoService.deleteToDo(id);
    }
}
