package planIT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import planIT.Entity.Notifications.Notification;
import planIT.Entity.Users.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class NotificationSystemTest {

    @LocalServerPort
    int port;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void notificationTestA() {

        // Create a User to Use with Notifications
        User r = new User("test", "password", "test@gmail.com");
        RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                post("/users");

        // Get Notifications Before Post Method
        Response response1 = RestAssured.given().
                when().
                get("/users/test/notifications");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        try {
            JSONArray array = new JSONArray(body1);
            assertEquals(0, array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Post Notification To Database
        Notification t = new Notification("test", "description");
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                post("/users/test/notifications");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        String body2 = response2.getBody().asString();
        assertEquals(success, body2);

        // Get Notifications After Post Method
        Response response3 = RestAssured.given().
                when().
                get("/users/test/notifications");

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
    public void notificationTestB() {

        // Get the Notification from the ID
        Response response = RestAssured.given().
                when().
                get("/notifications/1");

        JsonPath path = response.jsonPath();

        assertEquals("test", path.getString("title"));
    }

    @Test
    public void notificationTestC() {

        // Deletes Notifications From Database
        Response response = RestAssured.given().
                when().
                delete("users/test/notifications/1");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(success, body);

        // Get User Notifications After Delete Method
        Response response1 = RestAssured.given().
                when().
                get("/users/test/notifications");

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
