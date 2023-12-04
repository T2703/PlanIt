package planIT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import planIT.Entity.Events.Event;
import planIT.Entity.Users.User;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class EventSystemTest {

    @LocalServerPort
    int port;

    private String success = "{\"message\":\"success\"}";

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testA(){
        //create user for testing
        User testUser = new User("testUser", "password", "test@gmail.com");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(testUser).
                when().
                post("/users");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        //Post new event
        Event event1 = new Event("event1", "desc1", "loc 1", "Type 1", new Date(), new Date());
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(event1).
                when().
                post("/users/testUser/events");
        int statusCode2 = response1.getStatusCode();
        assertEquals(200, statusCode2);

    }

    @Test
    public void testB(){
        //get all events
        Response response1 = RestAssured.given().
                when().
                get("/events");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        // get event by id
        Response response2 = RestAssured.given().
                when().
                get("/events/1");
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        //get events by user
        Response response3 = RestAssured.given().
                when().
                get("/users/testUser/events");
        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);
    }

    @Test
    public void testC(){
        //create user for testing
        User testUser = new User("testUser2", "password", "test@gmail.com");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(testUser).
                when().
                post("/users");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        Response response2 = RestAssured.given().
                when().
                post("/users/testUser2/events/1");
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);
    }

    @Test
    public void testD(){
        //Update event
        Event event1Update = new Event("event1-Update", "desc1", "loc 1", "Type 1", new Date(), new Date());
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(event1Update).
                when().
                put("/events/1");
        int statusCode2 = response1.getStatusCode();
        assertEquals(200, statusCode2);
    }

    @Test
    public void testE(){
        Response response1 = RestAssured.given().
                when().
                delete("/users/testUser/events/1");
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        // Delete test users
        Response response2 = RestAssured.given().
                when().
                get("/username/testUser");
        JsonPath path = response2.jsonPath();
        RestAssured.given().
                when().
                delete("/users/" + path.getString("id"));
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        // Delete test users
        Response response3 = RestAssured.given().
                when().
                get("/username/testUser2");
        JsonPath path3 = response3.jsonPath();
        Response response4 = RestAssured.given().
                when().
                delete("/users/" + path3.getString("id"));
        int statusCode3 = response4.getStatusCode();
        assertEquals(200, statusCode3);

    }

}
