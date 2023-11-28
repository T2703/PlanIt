package planIT;

// Import Local classes
import planIT.Entity.Users.*;

// Import Java libraries
import java.util.List;
import java.util.ArrayList;

// import junit/spring tests
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.http.MediaType;

// import mockito related
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerUnit {

    @Autowired
    private MockMvc controller;

    @MockBean // note that this service is needed in my controller
    private UserService service;

    @MockBean // note that this repo is also needed in controller
    private UserRepository repo;

    private String success = "{\"message\":\"success\"}";

    /*
     * There are three steps here:
     *   1 - set up mock database methods
     *   2 - set up mock service methods
     *   3 - call and test the results of the controller
     */
    @Test
    public void testUserEndpoint() throws Exception {

        // Set up MOCK methods for the REPO
        List<User> l = new ArrayList<User>();

        // mock the findAll method
        when(repo.findAll()).thenReturn(l);

        // mock the save() method to save argument to the list
        when(repo.save((User)any(User.class)))
                .thenAnswer(x -> {
                    User r = x.getArgument(0);
                    l.add(r);
                    return null;
                });


        // Set up MOCK methods for the SERVICE

        // mock the create user method
        User r = new User();

        when(service.createUser(r)).thenReturn(success);


        // CALL THE CONTROLLER DIRECTLY (instead of using postman) AND TEST THE RESULTS
        controller.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(r.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].data", is(success)));
    }

}
