package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "<div>" +
            "<h1>People</h1>" +
            "<p>Please fill out the form below.</p>" +
            "<form action=\"/people\" method=\"post\">" +
            "<label for=\"firstName\">First Name: </label>" +
            "<input type=\"text\" id=\"firstName\" name=\"firstName\" required/><br><br>" +
            "<label for=\"lastName\">Last Name: </label>" +
            "<input type=\"text\" id=\"lastName\" name=\"lastName\" required/><br><br>" +
            "<label for=\"address\">Address: </label>" +
            "<input type=\"text\" id=\"address\" name=\"address\" required/><br><br>" +
            "<label for=\"telephone\">Phone Number: </label>" +
            "<input type=\"text\" id=\"telephone\" name=\"telephone\" required/><br><br><br>" +
            "<input type=\"submit\" value=\"Submit\"/>" +
            "</form>" +
            "</div>";
    }

}
