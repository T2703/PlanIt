package planIT.Entity.Assignments;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for assignment entity
 */
@RestController
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    /**
     * Returns all assignments from repository as a List object
     * @return assignments
     */
    @GetMapping(path = "/assignments")
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    /**
     * Gets an assignment from repository from a given id number.
     * Accesses assignmentService.getAssignmentById() method.
     * @param id id number of the target assignment
     * @return success
     */
    @GetMapping(path = "/assignments/{id}")
    public Assignment getAssignmentById(@PathVariable int id){
        return assignmentService.getAssignmentById(id);
    }

    /**
     * Access assignmentService.createAssignment() method.
     * Creates a new assignment and adds it to the given user
     * @param username username of target user
     * @param assignment newly created assignment
     * @return success
     */
    @PostMapping(path = "users/{username}/assignments")
    public String createAssignment(@PathVariable String username, @RequestBody Assignment assignment){
        return assignmentService.createAssignment(username, assignment);
    }

    /**
     * Accesses assignmentService.getAssignmentByUser() method.
     * Returns all assignments from a user as a List object
     * @param username username of target user
     * @return assignments
     */
    @GetMapping(path = "users/{username}/assignments")
    public List<Assignment> getUserAssignments(@PathVariable String username){
        return assignmentService.getAssignmentByUser(username);
    }

    /**
     * Accesses assignmentService.updateAssignment() method.
     * Updates an assignment entity in the repository.
     * @param id id number of target assignment
     * @param assignment assignment object with update info
     * @return success
     */
    @PutMapping(path = "/assignments/{id}")
    public Assignment updateAssignment(@PathVariable int id, @RequestBody Assignment assignment){
        return assignmentService.updateAssignment(id, assignment);
    }

    /**
     * Accesses assignmentService.deleteAssignment() method.
     * Deletes an assignment from the repository.
     * @param id id of assignment to delete
     * @return success
     */
    @DeleteMapping(path = "/assignments/{id}")
    public String deleteAssignment(@PathVariable int id){
        return assignmentService.deleteAssignment(id);
    }


}
