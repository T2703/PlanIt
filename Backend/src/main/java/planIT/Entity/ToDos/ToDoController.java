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

@RestController
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @GetMapping(path = "/ToDos")
    public List<ToDo> getAllToDos(){
        return toDoService.getAllToDos();
    }

    @GetMapping(path = "/ToDos/{id}")
    public ToDo getEventById(@PathVariable int id){
        return toDoService.getToDoById(id);
    }

    @PostMapping(path = "/ToDos")
    public String createToDo(@RequestBody ToDo ToDo){
        return toDoService.createToDo(ToDo);
    }

    @PutMapping(path = "/ToDos/{id}")
    public ToDo updateToDo(@PathVariable int id, @RequestBody ToDo toDo){
        return toDoService.updateToDo(id, toDo);
    }

    @DeleteMapping(path = "/ToDos/{id}")
    public String deleteToDo(@PathVariable int id){
        return toDoService.deleteToDo(id);
    }
}
