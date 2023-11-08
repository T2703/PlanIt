package planIT.Entity.Assignments;

import org.springframework.data.jpa.repository.JpaRepository;
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Assignment findById(int id);
    void deleteById(int id);

}
