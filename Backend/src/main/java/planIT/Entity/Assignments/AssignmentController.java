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


@RestController
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @GetMapping(path = "/assignments")
    public List<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping(path = "/assignments/{id}")
    public Assignment getAssignmentById(@PathVariable int id){
        return assignmentService.getAssignmentById(id);
    }

    @PostMapping(path = "users/{username}/assignments")
    public String createAssignment(@PathVariable String username, @RequestBody Assignment assignment){
        return assignmentService.createAssignment(username, assignment);
    }

    @GetMapping(path = "users/{username}/assignments")
    public List<Assignment> getUserAssignments(@PathVariable String username){
        return assignmentService.getAssignmentByUser(username);
    }

    @PutMapping(path = "/assignments/{id}")
    public Assignment updateAssignment(@PathVariable int id, @RequestBody Assignment assignment){
        return assignmentService.updateAssignment(id, assignment);
    }

    @DeleteMapping(path = "/assignments/{id}")
    public String deleteAssignment(@PathVariable int id){
        return assignmentService.deleteAssignment(id);
    }


}
