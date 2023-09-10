package coms309.people;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ex3 {

    //int a=3;
    Person Chris = new Person("Chris", "Smith", "321 Drury Ln", "515-867-5309");
    Person John = new Person("John", "Malarcky", "456 Notareal Rd", "903-242-2565");


    @GetMapping("/p1")
    public String p1(){
        return Chris.toString();
    }

    @GetMapping("/p2")
    public String p2(){
        return John.toString();
    }
}
