package planIT;


// Import Local classes
import io.restassured.path.json.JsonPath;
import org.glassfish.jaxb.runtime.v2.runtime.output.SAXOutput;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
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

import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

import java.util.Date;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestAssignmentController {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void A_testCreateAndDeleteAssignment(){

        System.out.println("testCreateAssignment");

        User user1 = new User("D", "D", "D");
        Response responseA = RestAssured.given().
                contentType("application/json").
                body(user1).
                when().
                post("/users");
        int statusCodeA = responseA.getStatusCode();
        assertEquals(200, statusCodeA);

        Assignment ass1 = new Assignment("Assignment1", "Desc1", "Course1", new Date(), false);
        Response response = RestAssured.given().
                contentType("application/json").
                body(ass1).
                when().
                post("users/D/assignments");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        System.out.print(response.getBody());

        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        System.out.println("testDeleteAssignment");

        Response response3 = RestAssured.given().
                when().
                delete("assignments/1");
        System.out.println(response);
        statusCode = response3.getStatusCode();
        assertEquals(200, statusCode);

        System.out.print(response3.getBody());

        returnString = response3.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);

        // DELETE TESTING USER
        Response response5 = RestAssured.given().
                when().
                get("/username/D");
        statusCode = response5.getStatusCode();
        assertEquals(200, statusCode);
        JsonPath path = response5.jsonPath();
        try {
            assertEquals("D", path.getString("username"));
            Response response1 = RestAssured.given().
                    when().
                    delete("/users/" + path.getString("id"));
        }catch(Exception e){
            System.out.println("");
        }
    }

    @Test
    public void B_testGetAssignmentById(){
        System.out.println("testGetAssignmentById");

        Response response = RestAssured.given().
                when().
                get("/assignments/1");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void C_testGetAllAssignments(){
        System.out.println("testGetAllAssignments");

        Response response = RestAssured.given().
                when().
                get("/assignments");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void D_testGetUserAssignments(){
        System.out.println("testGetUserAssignments");

        User user1 = new User("E", "E", "E");
        Response responseA = RestAssured.given().
                contentType("application/json").
                body(user1).
                when().
                post("/users");

        Response response = RestAssured.given().
                when().
                get("users/E/assignments");
        System.out.println(response);
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // DELTE TESTING USER
        Response response5 = RestAssured.given().
                when().
                get("/username/E");
        statusCode = response5.getStatusCode();
        assertEquals(200, statusCode);
        JsonPath path = response5.jsonPath();
        assertEquals("E", path.getString("username"));
        Response response1 = RestAssured.given().
                when().
                delete("/users/" + path.getString("id"));
    }

    @Test
    public void E_testUpdateAssignment(){
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
    }

    @Test
    public void F_testDeleteAssignment(){
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

        // DELTE TESTING USER
        Response response5 = RestAssured.given().
                when().
                get("/username/D");
        statusCode = response5.getStatusCode();
        assertEquals(200, statusCode);
        JsonPath path = response5.jsonPath();
        try {
            assertEquals("D", path.getString("username"));
            Response response1 = RestAssured.given().
                when().
                delete("/users/" + path.getString("id"));
        }catch(Exception e){
            System.out.println("");
        }
    }


}
