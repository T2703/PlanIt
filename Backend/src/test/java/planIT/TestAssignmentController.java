package planIT;


// Import Local classes
import org.glassfish.jaxb.runtime.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.annotation.Autowired;
import planIT.Entity.Assignments.Assignment;
import planIT.Entity.Chats.Chat;
import planIT.Entity.Messages.Message;
import planIT.Entity.Teams.Team;
import planIT.Entity.Users.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;

//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3
import planIT.Entity.Users.UserRepository;

import java.util.Date;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestAssignmentController {

    @LocalServerPort
    int port;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testCreateAssignment(){

        // Clear UserRepository Before Tests
        userRepository.deleteAll();

        System.out.println("testCreateAssignment");

        Assignment ass1 = new Assignment("Assignment1", "Desc1", "Course1", new Date(), false);
        Response response = RestAssured.given().
                contentType("application/json").
                body(ass1).
                when().
                post("users/A/assignments");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        System.out.print(response.getBody());

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testGetAssignmentById(){
        System.out.println("testGetAssignmentById");

        Response response = RestAssured.given().
                when().
                get("/assignments/1");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        //System.out.print(response.getBody());

        //String returnString = response.getBody().asString(); //TODO
        //assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testGetAllAssignments(){
        System.out.println("testGetAllAssignments");

        Response response = RestAssured.given().
                when().
                get("/assignments");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        //System.out.print(response.getBody());

        //String returnString = response.getBody().asString(); //TODO
        //assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testGetUserAssignments(){
        System.out.println("testGetAllAssignments");

        User userB = new User("B", "B", "B");
        Response response = RestAssured.given().
                contentType("application/json").
                body(userB).
                when().
                post("/users");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        System.out.println(response.getBody().asString());

        Response response2 = RestAssured.given().
                when().
                get("users/B/assignments");
        System.out.println(response);
        statusCode = response2.getStatusCode();
        assertEquals(200, statusCode);

        //System.out.print(response.getBody());
        //String returnString = response.getBody().asString(); //TODO
        //assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testUpdateAssignment(){
        System.out.println("testUpdateAssignments");

        Assignment assUp = new Assignment("Assignment1", "Ass1 UPDATE", "Course1", new Date(), false);
        Response response = RestAssured.given().
                contentType("application/json").
                body(assUp).
                when().
                put("assignments/1");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        System.out.print(response.getBody());

//        String returnString = response.getBody().asString();
//        assertEquals("{\"message\":\"success\"}", returnString);
    }

    @Test
    public void testDeleteAssignment(){
        System.out.println("testDeleteAssignment");

        Response response = RestAssured.given().
                when().
                delete("assignments/1");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        System.out.print(response.getBody());

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }


}
