package planIT.Assignments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


    @Service
    @Transactional
    public class AssignmentService {

        @Autowired
        private AssignmentRepository assignmentRepository;

        private String success = "{\"message\":\"success\"}";
        private String failure = "{\"message\":\"failure\"}";

        public List<Assignment> getAllAssignments() {
            return assignmentRepository.findAll();
        }

        public Assignment getAssignmentById(int id) {
            return assignmentRepository.findById(id);
        }

        public String createAssignment(Assignment assignment) {
            assignmentRepository.save(assignment);
            return success;
        }

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

        public String deleteAssignment(int id) {
            assignmentRepository.deleteById(id);
            return success;
        }
}
