package planIT.ToDos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    ToDo findById(int id);
    void deleteById(int id);
}
