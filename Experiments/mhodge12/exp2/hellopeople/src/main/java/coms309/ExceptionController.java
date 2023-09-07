package coms309;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
class ExceptionController {

    @RequestMapping(method = RequestMethod.GET, path = "/error")
    public String triggerException() {
        throw new RuntimeException("Check to see what happens when an exception is thrown.");
    }

}
