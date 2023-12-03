package planIT;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import planIT.Entity.ToDos.ToDo;
import planIT.Entity.ToDos.ToDoRepository;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ToDoSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    public ToDoRepository toDoRepository;

    @Autowired
    public UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void toDoTestA() {

        // Create a User to Use with ToDos
        User r = new User("test", "password", "test@gmail.com");
        RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                post("/users");

        // Get Users Before Post Method
        Response response1 = RestAssured.given().
                when().
                get("/ToDos");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        try {
            JSONArray array = new JSONArray(body1);
            assertEquals(0, array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Post User To Database
        ToDo t = new ToDo("test", "description", new Date("January 1, 2020 03:00:00"), new Date("January 1, 2020 02:30:00"));
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                post("/users/test/ToDos");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        String body2 = response2.getBody().asString();
        assertEquals(success, body2);

        // Get Users Before Post Method
        Response response3 = RestAssured.given().
                when().
                get("/ToDos");

        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);

        String body3 = response3.getBody().asString();
        try {
            JSONArray array = new JSONArray(body3);
            assertEquals(1, array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toDoTestB() {

        // Get the ToDos Before Put Method
        Response response = RestAssured.given().
                when().
                get("/ToDos/1");

        JsonPath path = response.jsonPath();

        assertEquals("test", path.getString("name"));

        // Updates the ToDos (Put)
        ToDo t = new ToDo("test-updated", "description", new Date("January 1, 2020 03:00:00"), new Date("January 1, 2020 02:30:00"));
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                put("/ToDos/1");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        assertEquals(success, body1);

        // Get the ToDos After Put Method
        Response response2 = RestAssured.given().
                when().
                get("/ToDos/1");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        JsonPath path2 = response2.jsonPath();

        assertEquals("test-updated", path2.getString("name"));
    }

    @Test
    public void toDoTestC() {

        // Should Return Null for the ToDos Does Not Exist
        ToDo t = new ToDo("test-updated", "description", new Date("January 1, 2020 03:00:00"), new Date("January 1, 2020 02:30:00"));
        Response response = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                put("/ToDos/2");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(failure, body);
    }

    @Test
    public void toDoTestD() {

        // Deletes ToDos From Database
        Response response = RestAssured.given().
                when().
                delete("users/test/ToDos/1");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(success, body);

        // Get ToDos After Delete Method
        Response response1 = RestAssured.given().
                when().
                get("/ToDos");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        try {
            JSONArray array = new JSONArray(body1);
            assertEquals(0, array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Get the "test" User ID
        Response response2 = RestAssured.given().
                when().
                get("/username/test");

        JsonPath path = response2.jsonPath();

        // Delete the User From the ID
        RestAssured.given().
                when().
                delete("/users/" + path.getString("id"));

    }
}
