package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.HashMap;

@RestController
public class PeopleController {

    HashMap<String, Person> peopleList = new HashMap<>();

    @GetMapping("/people")
    public @ResponseBody HashMap<String, Person> getAllPersons() { return peopleList; }

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

    @PostMapping("/people")
    public String processForm(@RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("address") String address,
                              @RequestParam("telephone") String telephone)
    {
        Person person = new Person(firstName, lastName, address, telephone);

        peopleList.put(firstName, person);

        return "<div><h1>Success</h1><p>You have submitted your form successfully! " + firstName + " has been successfully added!</p></div>";
    }

}
