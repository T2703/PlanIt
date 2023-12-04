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
import planIT.Entity.Tags.Tag;
import planIT.Entity.Users.User;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class TagSystemTest {

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
    public void tagTestA() {

        // Create a User to Use with Tags
        User r = new User("test", "password", "test@gmail.com");
        RestAssured.given().
                contentType("application/json").
                body(r).
                when().
                post("/users");

        // Get Tags Before Post Method
        Response response1 = RestAssured.given().
                when().
                get("/users/test/tags");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        try {
            JSONArray array = new JSONArray(body1);
            assertEquals(0, array.length());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Post Tag To Database
        Tag t = new Tag("test", "description");
        Response response2 = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                post("/users/test/tags");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        String body2 = response2.getBody().asString();
        assertEquals(success, body2);

        // Get Tags After Post Method
        Response response3 = RestAssured.given().
                when().
                get("/users/test/tags");

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
    public void tagTestB() {

        // Get the Tags Before Put Method
        Response response = RestAssured.given().
                when().
                get("/tags/1");

        JsonPath path = response.jsonPath();

        assertEquals("test", path.getString("name"));

        // Updates the Tags (Put)
        Tag t = new Tag("test-updated", "description");
        Response response1 = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                put("/tags/1");

        int statusCode1 = response1.getStatusCode();
        assertEquals(200, statusCode1);

        String body1 = response1.getBody().asString();
        assertEquals(success, body1);

        // Get the Tags After Put Method
        Response response2 = RestAssured.given().
                when().
                get("/tags/1");

        int statusCode2 = response2.getStatusCode();
        assertEquals(200, statusCode2);

        JsonPath path2 = response2.jsonPath();

        assertEquals("test-updated", path2.getString("name"));
    }

    @Test
    public void tagTestC() {

        // Should Return Null for the Tags Does Not Exist
        Tag t = new Tag("test-updated", "description");
        Response response = RestAssured.given().
                contentType("application/json").
                body(t).
                when().
                put("/tags/2");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(failure, body);
    }

    @Test
    public void teamTestD() {

        // Deletes Tags From Database
        Response response = RestAssured.given().
                when().
                delete("users/test/tags/1");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String body = response.getBody().asString();
        assertEquals(success, body);

        // Get User Tags After Delete Method
        Response response1 = RestAssured.given().
                when().
                get("/users/test/tags");

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
