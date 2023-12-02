package planIT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserRepository;
import planIT.Login.LoginRequest;
import planIT.Login.Password;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class UserSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    private UserRepository userRepository;

    private String success = "{\"message\":\"success\"}";

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void userTestA() {
        // Clear UserRepository Before Tests
        userRepository.deleteAll();

        // Get Users Before Post Method
        Response response1 = RestAssured.given().
                when().
                get("/users");

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
        User r = new User("test", "password", "test@gmail.com");
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                post("/users");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        String body2 = response2.getBody().asString();
        assertEquals(success, body2);

        // Get Users Before Post Method
        Response response3 = RestAssured.given().
                when().
                get("/users");

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
    public void userTestB() {

        // Get the "test" User ID
        Response response = RestAssured.given().
                when().
                get("/username/test");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        JsonPath path = response.jsonPath();
        assertEquals("test", path.getString("username"));

        // Get the User From the ID
        Response response1 = RestAssured.given().
                when().
                get("/users/" + path.getString("id"));

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        JsonPath path1 = response1.jsonPath();
        assertEquals("test", path1.getString("username"));

        // Update User "test" Info (Put)
        User r = new User("test-updated", "password", "test@gmail.com");
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                put("/users/" + path.getString("id"));

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        // Get The User From the ID
        Response response3 = RestAssured.given().
                when().
                get("/users/" + path.getString("id"));

        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);

        JsonPath path2 = response3.jsonPath();
        assertEquals("test-updated", path2.getString("username"));

    }

    @Test
    public void userTestC() {

        // Test Successful User Login Request
        LoginRequest request1 = new LoginRequest("test-updated", "password");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(request1).
                when().
                post("/login");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        assertEquals(success, body1);

        // Test Unsuccessful User Login Request
        LoginRequest request2 = new LoginRequest("test-updated", "incorrect-password");
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(request2).
                when().
                post("/login");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        String body2 = response2.getBody().asString();
        assertEquals("Incorrect Username or Password.", body2);

    }

    @Test
    public void userTestD() {

        // Changes the "test" User's Password
        Password password = new Password("password-updated");
        Response response = RestAssured.given().
                contentType("application/json").
                body(password).
                when().
                put("/change-password/test-updated");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(success, body);

        // Logs in the User with the New Password
        LoginRequest request1 = new LoginRequest("test-updated", "password-updated");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(request1).
                when().
                post("/login");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        assertEquals(success, body1);

    }

    @Test
    public void userTestE() {

        // Get the "test" User ID
        Response response = RestAssured.given().
                when().
                get("/username/test-updated");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        JsonPath path = response.jsonPath();

        // Delete the "test" User
        Response response1 = RestAssured.given().
                when().
                delete("/users/" + path.getString("id"));

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        assertEquals(success, body1);
    }

}
