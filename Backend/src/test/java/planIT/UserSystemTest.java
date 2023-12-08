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
import planIT.Entity.Users.User;
import planIT.Login.CanvasToken;
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

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void userTestA() {

        // Get Users Before Post Method
        Response response1 = RestAssured.given().
                when().
                get("/users");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        System.out.println("HERE>>>" +response1.getBody().asString()); //DEBUG
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
        Response response  = RestAssured.given().when().get("/users/test/canvas-token");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals("", body);

        CanvasToken token = new CanvasToken("token");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(token).
                when().
                put("/users/test/set-canvas-token");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        Response response2  = RestAssured.given().when().get("/users/test/canvas-token");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        String body2 = response2.getBody().asString();
        assertEquals("token", body2);

    }

    @Test
    public void userTestC() throws JSONException {
        User r = new User("test-add", "password", "test@gmail.com");
        RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                post("/users");

        Response response = RestAssured.given().when().get("/users/test/following");
        String body = response.getBody().asString();
        JSONArray arr = new JSONArray(body);
        assertEquals(0, arr.length());
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        Response response1 = RestAssured.given().when().get("/users/test-add/followers");
        String body1 = response1.getBody().asString();
        JSONArray arr1 = new JSONArray(body1);
        assertEquals(0, arr1.length());
        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        Response response2 = RestAssured.given().when().put("/users/test/add-follower/test-add");
        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        Response response3 = RestAssured.given().when().get("/users/test/following");
        String body3 = response3.getBody().asString();
        JSONArray arr3 = new JSONArray(body3);
        assertEquals(1, arr3.length());
        int statusCode3 = response3.getStatusCode();
        assertEquals(200, statusCode3);

        Response response4 = RestAssured.given().when().get("/users/test-add/followers");
        String body4 = response4.getBody().asString();
        JSONArray arr4 = new JSONArray(body4);
        assertEquals(1, arr4.length());
        int statusCode4 = response4.getStatusCode();
        assertEquals(200, statusCode4);

        Response response5 = RestAssured.given().when().put("/users/test/remove-follower/test-add");
        int statusCode5 = response5.getStatusCode();
        assertEquals(200, statusCode5);

        Response response6 = RestAssured.given().when().get("/users/test/following");
        String body6 = response6.getBody().asString();
        JSONArray arr6 = new JSONArray(body6);
        assertEquals(0, arr6.length());
        int statusCode6 = response6.getStatusCode();
        assertEquals(200, statusCode6);

        Response response7 = RestAssured.given().when().get("/users/test-add/followers");
        String body7 = response7.getBody().asString();
        JSONArray arr7 = new JSONArray(body7);
        assertEquals(0, arr7.length());
        int statusCode7 = response7.getStatusCode();
        assertEquals(200, statusCode7);
    }

    @Test
    public void userTestD() {

        // Should Not Create a User With The Same Username
        User r = new User("test", "password", "test@gmail.com");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                post("/users");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();

        assertEquals("Username already taken.", body1);

        // Should Not Create a User With Empty Fields
        User p = new User("", "", "");
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(p).
                when().
                post("/users");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        String body2 = response2.getBody().asString();

        assertEquals("Please complete all fields.", body2);

    }

    @Test
    public void userTestE() {

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
    public void userTestF() {

        // Should Return Null for the User Does Not Exist
        User r = new User("test", "password", "test@gmail.com");
        Response response = RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                put("/users/63");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(failure, body);

    }

    @Test
    public void userTestG() {

        // Test Successful User Login Request
        LoginRequest request1 = new LoginRequest("test-updated", "password");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(request1).
                when().
                post("/login");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        // Test Unsuccessful User Login Request
        LoginRequest request2 = new LoginRequest("test-updated", "incorrect-password");
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(request2).
                when().
                post("/login");

        int statusCode2 = response2.getStatusCode();
        System.out.println("login: " + response2);
        assertEquals(400, statusCode2);

        String body2 = response2.getBody().asString();
        assertEquals("Incorrect Username or Password.", body2);

    }

    @Test
    public void userTestH() {

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

    }

    @Test
    public void userTestI() {

        // Get the "test" User ID
        Response response = RestAssured.given().
                when().
                get("/username/test-updated");

        JsonPath path = response.jsonPath();

        // Delete the "test" User
        RestAssured.given().
                when().
                delete("/users/" + path.getString("id"));

        // Get the "test" User ID
        Response response1 = RestAssured.given().
                when().
                get("/username/test-add");

        JsonPath path1 = response1.jsonPath();

        // Delete the "test" User
        RestAssured.given().
                when().
                delete("/users/" + path1.getString("id"));

        // Get Users After Delete Method
        Response response2 = RestAssured.given().
                when().
                get("/users");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        String body2 = response2.getBody().asString();
        try {
            JSONArray array = new JSONArray(body2);
            assertEquals(0, array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
