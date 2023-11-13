package planIT.Entity.ToDos;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import planIT.Entity.Users.*;


@Service
@Transactional
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    public List<ToDo> getAllToDos() {
        return toDoRepository.findAll();
    }

    public ToDo getToDoById(int id) {
        return toDoRepository.findById(id);
    }

    public String createToDo(ToDo toDo) {
        toDoRepository.save(toDo);
        return success;
    }

    public ToDo updateToDo(int id, ToDo request) {
        ToDo toDo = toDoRepository.findById(id);
        if (toDo == null)
            return null;

        toDo.setName(request.getName());
        toDo.setDescription(request.getDescription());
        toDo.setRemindTime(request.getRemindTime());
        toDo.setDueDate(request.getDueDate());

        toDoRepository.save(toDo);
        return toDoRepository.findById(id);
    }

    public String deleteToDo(int id) {
        toDoRepository.deleteById(id);
        return success;
    }

    public String userAddToDo(String username, ToDo toDo){
        User user = userRepository.findByUsername(username);
        toDoRepository.save(toDo);
        user.getToDos().add(toDo);
        toDo.setUser(user);

        return success;
    }
}
