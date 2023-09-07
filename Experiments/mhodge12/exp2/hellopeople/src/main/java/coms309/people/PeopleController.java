package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

@RestController
public class PeopleController {

    HashMap<String, Person> peopleList = new HashMap<>();

    @GetMapping("/people")
    public @ResponseBody HashMap<String, Person> getAllPersons() { return peopleList; }

    @PostMapping("/people")
    public @ResponseBody String createPerson(@RequestBody Person person) {
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person " + person.getFirstName() + " Saved";
    }

    @GetMapping("/people/{firstName}")
    public @ResponseBody Person getPerson(@PathVariable String firstName) {
        Person p = peopleList.get(firstName);
        return p;
    }

    @PutMapping("/people/{firstName}")
    public @ResponseBody Person updatePerson(@PathVariable String firstName, @RequestBody Person p) {
        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    @DeleteMapping("/people/{firstName}")
    public @ResponseBody HashMap<String, Person> deletePerson(@PathVariable String firstName) {
        peopleList.remove(firstName);
        return peopleList;
    }

}
