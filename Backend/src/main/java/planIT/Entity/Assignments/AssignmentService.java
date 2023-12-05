package planIT.Entity.Assignments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import planIT.Entity.Users.UserRepository;

/**
 * Service class for assignment entity
 */
@Service
    @Transactional
    public class AssignmentService {

        @Autowired
        private AssignmentRepository assignmentRepository;

        @Autowired
        private UserRepository userRepository;

        private String success = "{\"message\":\"success\"}";
        private String failure = "{\"message\":\"failure\"}";

    /**
     * Returns all assignments from repository as a List object
     * @return Assignment List
     */

    public List<Assignment> getAllAssignments() {
            return assignmentRepository.findAll();
        }

    /**
     * Gets an assignment from repository by its id number
     * @param id id number of target assignment
     * @return success
     */
    public Assignment getAssignmentById(int id) {
            return assignmentRepository.findById(id);
        }

        public String createAssignment(String username, Assignment assignment) {
            assignment.setUser(userRepository.findByUsername(username));
            assignmentRepository.save(assignment);
            return success;
        }

    /**
     * Updates an assignment in the repository.
     * @param id id of target assignment
     * @param request Assignment object with updated details
     * @return updated assignment
     */
        public Assignment updateAssignment(int id, Assignment request) {
            Assignment assignment = assignmentRepository.findById(id);
            if (assignment == null)
                return null;

            assignment.setTitle(request.getTitle());
            assignment.setDescription(request.getDescription());
            assignment.setCourse(request.getCourse());
            assignment.setDueDate(request.getDueDate());
            assignment.setIsCompleted(request.getIsCompleted());

            assignmentRepository.save(assignment);
            return assignmentRepository.findById(id);
        }

    /**
     * Deletes an assignment from the repository
     * @param id id number of the assignment to be deleted
     * @return success
     */
    public String deleteAssignment(int id) {
            assignmentRepository.deleteById(id);
            return success;
    }

    /**
     * Gets all assignments for a particular user and returns them as a list object.
     * @param username username of the target user
     * @return assignment List
     */
    public List<Assignment> getAssignmentByUser(String username) {
        return userRepository.findByUsername(username).getAssignments();
    }
}
